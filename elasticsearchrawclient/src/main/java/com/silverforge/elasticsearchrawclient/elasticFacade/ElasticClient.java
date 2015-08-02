package com.silverforge.elasticsearchrawclient.elasticFacade;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.silverforge.elasticsearchrawclient.ElasticClientApp;
import com.silverforge.elasticsearchrawclient.R;
import com.silverforge.elasticsearchrawclient.connector.Connectable;
import com.silverforge.elasticsearchrawclient.connector.Connector;
import com.silverforge.elasticsearchrawclient.connector.ConnectorSettings;
import com.silverforge.elasticsearchrawclient.elasticFacade.mappers.AliasParser;
import com.silverforge.elasticsearchrawclient.elasticFacade.mappers.ElasticClientMapper;
import com.silverforge.elasticsearchrawclient.elasticFacade.model.AddDocumentResult;
import com.silverforge.elasticsearchrawclient.elasticFacade.model.InvokeResult;
import com.silverforge.elasticsearchrawclient.exceptions.IndexCannotBeNullException;
import com.silverforge.elasticsearchrawclient.utils.StreamUtils;
import com.silverforge.elasticsearchrawclient.utils.StringUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

// TODO : create interface for elasticclient once the methods are implemented
// TODO : QueryManagr should be created to maintain and reuse certain queries by user
public class ElasticClient {
	private static final String TAG = ElasticClient.class.getName();
	private static final String STRING_EMPTY = "";
	private Connectable connector;
	// TODO : use ElasticClientMapper instead
	private ObjectMapper mapper = new ObjectMapper();

	public Raw raw = new Raw();

	public ElasticClient(Connectable connector)
		throws MalformedURLException {
		this.connector = connector;
	}

	public ElasticClient(ConnectorSettings settings)
		throws URISyntaxException {
		connector = new Connector(settings);
	}

	public boolean createIndex(String indexName, String data) {
		InvokeResult result;

		if (indexName.startsWith("/"))
			result = connector.put(indexName, data);
		else
			result = connector.put("/" + indexName, data);

		return result.isSuccess();
	}

	public void addAlias(String indexName, String aliasName) {
		String addAliasTemplate
			= StreamUtils.getRawContent(ElasticClientApp.getAppContext(), R.raw.add_alias);

		String data = addAliasTemplate
				.replace("{{INDEXNAME}}", indexName)
				.replace("{{ALIASNAME}}", aliasName);

		connector.post("/_aliases", data);
	}

	public void removeIndices() {
		removeIndices(connector.getSettings().getIndices());
	}

	public void removeIndices(String[] indexNames) {
		for (String indexName : indexNames) {
			if (indexName.startsWith("/"))
				connector.delete(indexName);
			else
				connector.delete("/" + indexName);
		}
	}

	public boolean indexExists(String indexName) {
		InvokeResult result;

		if (indexName.startsWith("/"))
			result = connector.head(indexName);
		else
			result = connector.head("/" + indexName);

		return result.isSuccess();
	}

	public List<String> getAliases(String index) {
		ArrayList<String> retValue = new ArrayList<>();
		String getPath = String.format("/%s/_aliases", index);
		InvokeResult invokeResult = connector.get(getPath);
		if (invokeResult.isSuccess()) {
			List<String> aliasesFromJson = AliasParser.getAliasesFromJson(index, invokeResult.getResult());
			retValue.addAll(aliasesFromJson);
		}
		return retValue;
	}

	public void removeAlias(String indexName, String aliasName) {
		String addAliasTemplate
			= StreamUtils.getRawContent(ElasticClientApp.getAppContext(), R.raw.remove_alias);

		String data = addAliasTemplate
				.replace("{{INDEXNAME}}", indexName)
				.replace("{{ALIASNAME}}", aliasName);

		connector.post("/_aliases", data);
	}

	public <T> String addDocument(T entity)
			throws IndexCannotBeNullException, IllegalArgumentException {

		return addDocument(null, entity);
	}

	public <T> String addDocument(String id, T entity)
			throws IndexCannotBeNullException, IllegalArgumentException {

		if (entity == null)
			throw new IllegalArgumentException("entity cannot be null");

		String retValue = STRING_EMPTY;
		try {
			String entityJson = mapper.writeValueAsString(entity);
			String addPath = getOperationPath(id, OperationType.CREATE);

			InvokeResult result = connector.post(addPath, entityJson);
			AddDocumentResult addDocumentResult = mapper.readValue(result.getResult(), AddDocumentResult.class);
			retValue = addDocumentResult.getId();
		} catch (IndexCannotBeNullException ie) {
			throw ie;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retValue;
	}

	public <T> String addDocument(String index, String type, String id, T entity)
			throws IllegalArgumentException {

		if (entity == null)
			throw new IllegalArgumentException("entity cannot be null");

		if (TextUtils.isEmpty(index))
			throw new IllegalArgumentException("index cannot be null or empty");

		if (TextUtils.isEmpty(type))
			throw new IllegalArgumentException("type cannot be null or empty");

		if (TextUtils.isEmpty(id))
			throw new IllegalArgumentException("id cannot be null or empty");

        String retValue = STRING_EMPTY;
		try {
			String entityJson = mapper.writeValueAsString(entity);
			String addPath = String.format("/%s/%s/%s", index, type, id);

			InvokeResult result = connector.post(addPath, entityJson);
			AddDocumentResult addDocumentResult = mapper.readValue(result.getResult(), AddDocumentResult.class);
			retValue = addDocumentResult.getId();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return retValue;
    }

	public void removeDocument(String id)
			throws IllegalArgumentException, IndexCannotBeNullException {

		if (TextUtils.isEmpty(id))
			throw new IllegalArgumentException("id cannot be null or empty");

		String deletePath = getOperationPath(id, OperationType.DELETE);
		connector.delete(deletePath);
	}

	public void removeDocument(String index, String type, String id)
			throws IllegalArgumentException{

		if (TextUtils.isEmpty(index))
			throw new IllegalArgumentException("index cannot be null or empty");

		if (TextUtils.isEmpty(type))
			throw new IllegalArgumentException("type cannot be null or empty");

		if (TextUtils.isEmpty(id))
			throw new IllegalArgumentException("id cannot be null or empty");

		String deletePath = String.format("/%s/%s/%s", index, type, id);
		connector.delete(deletePath);
	}

	public void removeDocumentsQuery(String query) {
		String deletePath
			= getDeleteQueryPath(connector.getSettings().getIndices(),
				connector.getSettings().getTypes());
		connector.delete(deletePath, query);
	}

	public void removeDocumentsQuery(String[] indices, String[] types, String query) {
		String deleteQueryPath = getDeleteQueryPath(indices, types);
		connector.delete(deleteQueryPath.toString(), query);
	}

	public <T> void updateDocument(String id, T entity)
			throws IndexCannotBeNullException {

		if (entity == null)
			throw new IllegalArgumentException("entity cannot be null");

		try {
			String entityJson = mapper.writeValueAsString(entity);
			String updatePath = getOperationPath(id, OperationType.UPDATE);

			String updateTemplate = StreamUtils.getRawContent(ElasticClientApp.getAppContext(), R.raw.update_template);
			String data = updateTemplate.replace("{{ENTITYJSON}}", entityJson);

			connector.post(updatePath, data);
		} catch (IndexCannotBeNullException ie) {
			throw ie;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }

    public <T> void updateDocument(String index, String type, String id, T entity)
			throws IllegalArgumentException {

		if (entity == null)
			throw new IllegalArgumentException("entity cannot be null");

		if (TextUtils.isEmpty(index))
			throw new IllegalArgumentException("index cannot be null or empty");

		if (TextUtils.isEmpty(type))
			throw new IllegalArgumentException("type cannot be null or empty");

		if (TextUtils.isEmpty(id))
			throw new IllegalArgumentException("id cannot be null or empty");

		try {
			String entityJson = mapper.writeValueAsString(entity);
			String updatePath = String.format("/%s/%s/%s/_update", index, type, id);

			String updateTemplate = StreamUtils.getRawContent(ElasticClientApp.getAppContext(), R.raw.update_template);
			String data = updateTemplate.replace("{{ENTITYJSON}}", entityJson);

			connector.post(updatePath, data);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void bulk() {

    }

	public <T> List<T> getDocument(String[] ids, Class<T> classType) {
		return getDocument(null, ids, classType);
	}

	public <T> List<T> getDocument(String type, String[] ids, Class<T> classType) {
		return getDocument(null, type, ids, classType);
	}

	public <T> List<T> getDocument(String index, String type, String[] ids, Class<T> classType) {
		String queryTemplate;
		String queryIds = StringUtils.makeCommaSeparatedListWithQuotationMark(ids);
		if (TextUtils.isEmpty(type))
			queryTemplate = StreamUtils.getRawContent(ElasticClientApp.getAppContext(), R.raw.search_by_ids);
		else
			queryTemplate = StreamUtils.getRawContent(ElasticClientApp.getAppContext(), R.raw.search_by_ids_and_type);

		String query;
		if (TextUtils.isEmpty(type))
			query = queryTemplate.replace("{{IDS}}", queryIds);
		else
			query = queryTemplate
						.replace("{{IDS}}", queryIds)
						.replace("{{TYPE}}", type);

		String queryPath;
		if (TextUtils.isEmpty(index))
			queryPath = getQueryPath();
		else
			queryPath = String.format("/%s/_search", index);
		String documents = connector.post(queryPath, query).getResult();

		ElasticClientMapper<T> mapper = new ElasticClientMapper<>();
		return mapper.mapToList(documents, classType);
	}

	public <T> List<T> search(String query, Class<T> classType) {
		String queryPath = getQueryPath();
		String documents = connector.post(queryPath, query).getResult();

		ElasticClientMapper<T> mapper = new ElasticClientMapper<>();
		return mapper.mapToList(documents, classType);
	}

	public <T> List<T> search(String index, String query, Class<T> classType) {
		if (TextUtils.isEmpty(index))
			throw new IllegalArgumentException("index cannot be null or empty");

		String queryPath = String.format("/%s/_search", index);
		String documents = connector.post(queryPath, query).getResult();

		ElasticClientMapper<T> mapper = new ElasticClientMapper<>();
		return mapper.mapToList(documents, classType);
	}

	protected String getOperationPath(String id, OperationType operationType)
			throws IndexCannotBeNullException {

		boolean indicesAreEmpty = true;

		StringBuilder pathBuilder = new StringBuilder();

		String[] indices = connector.getSettings().getIndices();
		if (indices != null && indices.length > 0) {
			indicesAreEmpty = false;
			pathBuilder.append("/");
			String indicesPath = StringUtils.makeCommaSeparatedList(indices);
			pathBuilder.append(indicesPath);
		}

		if (indicesAreEmpty)
			throw new IndexCannotBeNullException();

		String[] types = connector.getSettings().getTypes();
		if (types != null && types.length > 0) {
			pathBuilder.append("/");
			String typesPath = StringUtils.makeCommaSeparatedList(types);
			pathBuilder.append(typesPath);
		}

		if (!TextUtils.isEmpty(id))	{
			pathBuilder.append("/").append(id);

			if (operationType != null) {
				String operationPath = operationType.getOperationTypePath();
				if (!TextUtils.isEmpty(operationPath))
					pathBuilder.append("/").append(operationPath);
			}
		}

		return pathBuilder.toString();
	}

	protected String getQueryPath() {
		boolean indicesAreEmpty = true;
		boolean typesAreEmpty = true;

		StringBuilder pathBuilder = new StringBuilder();

		String[] indices = connector.getSettings().getIndices();
		if (indices != null && indices.length > 0) {
			indicesAreEmpty = false;
			pathBuilder.append("/");
			String indicesPath = StringUtils.makeCommaSeparatedList(indices);
			pathBuilder.append(indicesPath);
		}

		String[] types = connector.getSettings().getTypes();
		if (types != null && types.length > 0) {
			typesAreEmpty = false;
			if (indicesAreEmpty)
				pathBuilder.append("/_all");

			pathBuilder.append("/");
			String typesPath = StringUtils.makeCommaSeparatedList(types);
			pathBuilder.append(typesPath);
		}

		if (indicesAreEmpty && typesAreEmpty)
			pathBuilder.append("/_all");

		pathBuilder.append("/_search");
		return pathBuilder.toString();
	}

	@NonNull
	protected String getDeleteQueryPath(String[] indices, String[] types) {
		StringBuilder pathBuilder = new StringBuilder();
		if (indices == null || indices.length == 0)
			pathBuilder.append("/_all");
		else {
			String indexList = StringUtils.makeCommaSeparatedList(indices);
			pathBuilder
					.append("/")
					.append(indexList);
		}

		if (types != null && types.length > 0) {
			String typeList = StringUtils.makeCommaSeparatedList(types);
			pathBuilder
					.append("/")
					.append(typeList);
		}

		pathBuilder.append("/_query");
		return pathBuilder.toString();
	}

	public final class Raw {

		private Raw() {}

		public InvokeResult head(String path) {
			return connector.head(path);
		}

		public InvokeResult get(String path) {
			return connector.get(path);
		}

		public InvokeResult post(String path, String data) {
			return connector.post(path, data);
		}

		public InvokeResult put(String path, String data) {
			return connector.put(path, data);
		}

		public InvokeResult delete(String path, String data) {
			return connector.delete(path, data);
		}
	}
}

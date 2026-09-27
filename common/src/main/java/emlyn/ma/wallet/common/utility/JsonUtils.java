package emlyn.ma.wallet.common.utility;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

@UtilityClass
public class JsonUtils {

    private static final JsonMapper JSON_MAPPER;

    static {
        JSON_MAPPER = JsonMapper.builder().build();
    }

    @SneakyThrows
    public String toJson(Object object) {
        return JSON_MAPPER.writeValueAsString(object);
    }

    @SneakyThrows
    public <T> T toObject(String json, TypeReference<T> typeReference) {
        return JSON_MAPPER.readValue(json, typeReference);
    }

    @SneakyThrows
    public <T> T toObject(String json, Class<T> type) {
        return JSON_MAPPER.readValue(json, type);
    }

    @SneakyThrows
    public <E> List<E> toList(String json, Class<E> type) {
        var collectionType = JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, type);
        return JSON_MAPPER.readValue(json, collectionType);
    }

    @SneakyThrows
    public <V> Map<String, V> toMap(String json, Class<V> type) {
        var mapType = JSON_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, type);
        return JSON_MAPPER.readValue(json, mapType);
    }

}
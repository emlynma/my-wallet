package emlyn.ma.wallet.common.utility;

import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JsonUtilsTest {

    record Person(String name, int age) {
    }

    @Test
    void toJson_serializesObjectToJson() {
        String json = JsonUtils.toJson(new Person("Alice", 30));
        assertThat(json).isEqualTo("{\"name\":\"Alice\",\"age\":30}");
    }

    @Test
    void toJson_ofNull_returnsJsonNull() {
        assertThat(JsonUtils.toJson(null)).isEqualTo("null");
    }

    @Test
    void toObject_withClass_deserializesObject() {
        Person person = JsonUtils.toObject("{\"name\":\"Alice\",\"age\":30}", Person.class);
        assertThat(person).isEqualTo(new Person("Alice", 30));
    }

    @Test
    void toObject_withTypeReference_deserializesGenericType() {
        List<Person> list = JsonUtils.toObject(
                "[{\"name\":\"a\",\"age\":1},{\"name\":\"b\",\"age\":2}]",
                new TypeReference<>() {
                });
        assertThat(list).containsExactly(new Person("a", 1), new Person("b", 2));
    }

    @Test
    void toList_deserializesList() {
        List<Integer> list = JsonUtils.toList("[1,2,3]", Integer.class);
        assertThat(list).containsExactly(1, 2, 3);
    }

    @Test
    void toMap_deserializesMap() {
        Map<String, Integer> map = JsonUtils.toMap("{\"a\":1,\"b\":2}", Integer.class);
        assertThat(map).containsEntry("a", 1).containsEntry("b", 2);
    }

    @Test
    void toObject_withInvalidJson_throwsException() {
        assertThatThrownBy(() -> JsonUtils.toObject("{invalid", Person.class))
                .isInstanceOf(Exception.class);
    }
}

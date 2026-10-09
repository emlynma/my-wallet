package emlyn.ma.wallet.component.id;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SnowflakeTest {

    private static final int MAX_CENTER_ID = 31;
    private static final int MAX_WORKER_ID = 31;

    @Test
    void nextId_generatesUniqueIds() {
        Snowflake snowflake = new Snowflake(1, 1);
        int count = 100_000;
        Set<Long> ids = IntStream.range(0, count)
                .mapToObj(i -> snowflake.nextId())
                .collect(Collectors.toSet());
        assertThat(ids).hasSize(count);
    }

    @Test
    void nextId_generatesUniqueIdsConcurrently() {
        Snowflake snowflake = new Snowflake(1, 1);
        int count = 100_000;
        Set<Long> ids = IntStream.range(0, count)
                .parallel()
                .mapToObj(i -> snowflake.nextId())
                .collect(Collectors.toSet());
        assertThat(ids).hasSize(count);
    }

    @Test
    void nextId_generatesIdsInIncreasingOrder() {
        Snowflake snowflake = new Snowflake(1, 1);
        long previous = snowflake.nextId();
        for (int i = 0; i < 10_000; i++) {
            long current = snowflake.nextId();
            assertThat(current).isGreaterThan(previous);
            previous = current;
        }
    }

    @Test
    void nextId_returnsPositiveIds() {
        Snowflake snowflake = new Snowflake(1, 1);
        for (int i = 0; i < 10_000; i++) {
            assertThat(snowflake.nextId()).isPositive();
        }
    }

    @Test
    void nextId_encodesCenterIdAndWorkerId() {
        Snowflake snowflake = new Snowflake(5, 3);
        long id = snowflake.nextId();
        // 位布局：41 位时间戳 - 5 位 workerId - 5 位 centerId - 12 位序列号
        assertThat((id >>> 12) & 0x1F).isEqualTo(5);
        assertThat((id >>> 17) & 0x1F).isEqualTo(3);
    }

    @Test
    void constructor_acceptsBoundaryValues() {
        new Snowflake(0, 0);
        new Snowflake(MAX_CENTER_ID, MAX_WORKER_ID);
    }

    @Test
    void constructor_rejectsInvalidCenterId() {
        assertThatThrownBy(() -> new Snowflake(-1, 0))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Snowflake(MAX_CENTER_ID + 1, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_rejectsInvalidWorkerId() {
        assertThatThrownBy(() -> new Snowflake(0, -1))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Snowflake(0, MAX_WORKER_ID + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

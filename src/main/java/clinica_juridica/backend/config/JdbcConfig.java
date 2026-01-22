package clinica_juridica.backend.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.lang.NonNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @NonNull
    @SuppressWarnings("null")
    public List<Object> userConverters() {
        return Arrays.asList(
                new JsonNodeToPGobjectConverter(),
                new PGobjectToJsonNodeConverter());
    }

    @WritingConverter
    class JsonNodeToPGobjectConverter implements Converter<JsonNode, PGobject> {
        @Override
        public PGobject convert(@NonNull JsonNode source) {
            PGobject jsonObject = new PGobject();
            jsonObject.setType("jsonb");
            try {
                jsonObject.setValue(objectMapper.writeValueAsString(source));
            } catch (SQLException | JsonProcessingException e) {
                throw new RuntimeException("Error converting JsonNode to PGobject", e);
            }
            return jsonObject;
        }
    }

    @ReadingConverter
    class PGobjectToJsonNodeConverter implements Converter<PGobject, JsonNode> {
        @Override
        public JsonNode convert(@NonNull PGobject source) {
            String value = source.getValue();
            try {
                return objectMapper.readTree(value);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting PGobject to JsonNode", e);
            }
        }
    }
}

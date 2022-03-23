package pl.baranowski.dev.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.baranowski.dev.dto.IngredientDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= ObjectMapper.class)
class IngredientDTODeserializerTest {
    @Autowired
    ObjectMapper underTest;

    @Test
    void deserialize() throws JsonProcessingException {
        //given
        String json = "{\"id\":93607,\"aisle\":\"Milk,Eggs,OtherDairy\",\"image\":\"almond-milk.png\",\"consistency\":\"liquid\",\"name\":\"almond milk\",\"nameClean\":\"almondmilk\",\"original\":\"11/2cupunsweetenedalmondmilk\",\"originalName\":\"unsweetenedalmondmilk\",\"amount\":1.5,\"unit\":\"cup\",\"meta\":[\"unsweetened\"],\"measures\":{\"us\":{\"amount\":1.5,\"unitShort\":\"cups\",\"unitLong\":\"cups\"},\"metric\":{\"amount\":354.882,\"unitShort\":\"ml\",\"unitLong\":\"milliliters\"}}}\n";
        //when
        IngredientDTO result = underTest.readValue(json, IngredientDTO.class);
        //then
        IngredientDTO expected = new IngredientDTO("almond milk", 1.5D, "cup");
        assertEquals(expected, result);
    }
}
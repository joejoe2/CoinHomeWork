package com.example.coin.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;

public class RawBpiDeserializer extends StdDeserializer {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public RawBpiDeserializer() {
    super(RawBpiDeserializer.class);
  }

  protected RawBpiDeserializer(Class vc) {
    super(vc);
  }

  @Override
  public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    RawBPI rawBpi = new RawBPI();
    TreeNode json = jsonParser.getCodec().readTree(jsonParser);
    Instant updatedAt =
        Instant.parse(objectMapper.treeToValue(json.get("time").get("updatedISO"), String.class));
    rawBpi.setUpdatedAt(updatedAt);
    rawBpi.setIndices(
        new ArrayList<>(
            objectMapper
                .readValue(
                    json.get("bpi").traverse(), new TypeReference<Map<String, RawIndex>>() {})
                .values()));
    return rawBpi;
  }
}

package cn.pledge.envconsole.common.serializer;

import cn.pledge.envconsole.common.utils.NumberUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/** @author 1244 */
public class Decimal6Serializer extends JsonSerializer<Object> {
  @Override
  public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(NumberUtils.decimalWithScale(value, 6));
  }
}

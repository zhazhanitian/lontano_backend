package cn.pledge.envconsole.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** @author 1244 */
public class NumberUtils {
  public static String decimalWithScale(Object value, int scale) {
    if (Objects.nonNull(value)) {
      if (value instanceof Double) {
        Double doubleValue = (Double) value;
        if (!doubleValue.isInfinite() && !doubleValue.isNaN()) {
          return decimalWithScale(BigDecimal.valueOf(doubleValue), scale);
        }
      } else if (value instanceof String) {
        String stringValue = (String) value;
        try {
          if (StringUtils.isNotEmpty(stringValue)) {
            return decimalWithScale(new BigDecimal(stringValue), scale);
          }
          new BigDecimal((String) value);
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      } else if (value instanceof Integer) {
        Integer integerValue = (Integer) value;
        return decimalWithScale(BigDecimal.valueOf(integerValue), scale);
      } else if (value instanceof Long) {
        Long longValue = (Long) value;
        return decimalWithScale(BigDecimal.valueOf(longValue), scale);
      } else if (value instanceof Float) {
        Float floatValue = (Float) value;
        return decimalWithScale(BigDecimal.valueOf(floatValue), scale);
      } else if (value instanceof BigDecimal) {
        BigDecimal bigDecimalValue = (BigDecimal) value;
        return decimalWithScale(bigDecimalValue, scale);
      }
    }
    return "0.00";
  }

  public static String decimalWithScale(BigDecimal value, int scale) {
    return value.setScale(scale, RoundingMode.HALF_EVEN).toString();
  }
}

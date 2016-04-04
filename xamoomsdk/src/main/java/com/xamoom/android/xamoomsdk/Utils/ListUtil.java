package com.xamoom.android.xamoomsdk.Utils;

import java.util.List;

/**
 * ListUtil to join lists of string.
 * (Because TextUtil gets mocked in tests)
 */
public class ListUtil {
  /**
   * Will return joined list with seperator.
   *
   * @param list List of Strings.
   * @param seperator String to seperate them.
   * @return Joined list.
   */
  public static String joinStringList(List<String> list, String seperator) {
    if (list == null || seperator == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    int counter = 0;
    for (String item : list) {
      counter++;
      sb.append(item);
      if (counter != list.size()) {
        sb.append(seperator);
      }
    }
    return sb.toString();
  }
}

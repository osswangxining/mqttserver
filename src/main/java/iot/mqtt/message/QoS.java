package iot.mqtt.message;

public enum QoS {
  AT_MOST_ONCE(0), AT_LEAST_ONCE(1), EXACTLY_ONCE(2);

  final public int value;

  QoS(int value) {
    this.value = value;
  }

  static QoS valueOf(int i) {
    for (QoS q : QoS.values()) {
      if (q.value == i)
        return q;
    }
    throw new IllegalArgumentException("Not a valid QoS number: " + i);
  }

  public int getValue() {
    return value;
  }
}

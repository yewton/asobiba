package net.yewton.asobiba.btrace;

import static org.openjdk.btrace.core.BTraceUtils.Strings;
import static org.openjdk.btrace.core.BTraceUtils.print;
import static org.openjdk.btrace.core.BTraceUtils.println;

import org.openjdk.btrace.core.BTraceUtils;
import org.openjdk.btrace.core.annotations.BTrace;
import org.openjdk.btrace.core.annotations.OnMethod;
import org.openjdk.btrace.core.annotations.ProbeClassName;
import org.openjdk.btrace.core.annotations.ProbeMethodName;

/**
 * This script traces method entry into every method of
 * every class in javax.swing package! Think before using
 * this script -- this will slow down your app significantly!!
 */
@BTrace public class AllMethods {
  @OnMethod(
      clazz="/^(net\\.yewton\\.asobiba|org\\.springframework\\.security\\.web\\.authentication)\\..*/",
      method="/^[^$<>]+$/"
  )
  public static void m(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod) {
    print(probeClass + "#" + probeMethod);
  }
}

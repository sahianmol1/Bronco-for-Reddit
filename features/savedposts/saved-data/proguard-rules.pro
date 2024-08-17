-keep class com.anmolsahi.data.local.** { *; }

# These rules were asked to be added by the R8 itself through build error while
# buidling release apk
-dontwarn java.lang.invoke.StringConcatFactory
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Room database
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**
-dontwarn org.slf4j.impl.StaticLoggerBinder

-dontwarn com.anmolsahi.data.di.DataStoreModule_ProvidePreferencesDataStoreFactory
-dontwarn com.anmolsahi.data.di.DataStoreModule_ProvidePreferencesDataStoreRepositoryFactory
-dontwarn com.anmolsahi.data.di.HomeDataModule_ProvideHomeRepositoryFactory
-dontwarn com.anmolsahi.data.di.HomeDataModule_ProvidesHomeApiRequestsFactory
-dontwarn com.anmolsahi.data.remote.HomeService
-dontwarn com.anmolsahi.postdetailspresentation.postdetails.navigation.PostDetailsNavGraphKt
-dontwarn com.anmolsahi.presentation.navigation.AboutUsNavgraphKt
-dontwarn com.anmolsahi.presentation.navigation.HomeNavgraphKt
-dontwarn com.anmolsahi.presentation.navigation.SavedPostsNavigationKt
-dontwarn com.anmolsahi.presentation.navigation.SearchNavgraphKt
-dontwarn com.anmolsahi.designsystem.theme.ThemeKt
-dontwarn com.anmolsahi.designsystem.uicomponents.BRNavigationBarKt
-dontwarn com.anmolsahi.designsystem.utils.TopLevelDestinationKt
-dontwarn com.anmolsahi.navigation.BRNavHostKt

# Firebase Crashlytics
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

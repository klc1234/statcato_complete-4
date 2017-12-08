<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE helpset
  PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN"
          "http://java.sun.com/products/javahelp/helpset_1_0.dtd">

<helpset version="2.0">

   <!-- title -->
   <title>Statcato</title>

   <!-- maps -->
   <maps>
      <homeID>overview</homeID>
      <mapref location="StatcatoMap.jhm" />
   </maps>

   <!-- views -->
   <view>
      <name>TOC</name>
      <label>TOC</label>
      <type>javax.help.TOCView</type>
      <data>StatcatoTOC.xml</data>
   </view>

   <view>
      <name>Index</name>
      <label>Index</label>
      <type>javax.help.IndexView</type>
      <data>StatcatoIndex.xml</data>
   </view>
   
   <view>
      <name>Favorite</name>
      <label>Favorite</label>
      <type>javax.help.FavoritesView</type>
   </view>

   <!--
  <view>
    <name>Glossary</name>
    <label>Glossary</label>
    <type>javax.help.GlossaryView</type>
    <data>StatcatoGlossary.xml</data>
 </view>
   -->

<view>
	<name>Search</name>
	<label>Search</label>
	<type>javax.help.SearchView</type>
	<data engine="com.sun.java.help.search.DefaultSearchEngine">JavaHelpSearch</data>
   </view>

  
  <!-- presentations -->
   <presentation default="true">
       <name>Main_Window</name>
       <size width="800" height="600" />
       <location x="0" y="0" />
       <title>Statcato</title>
       <image>small-logo</image>
       <toolbar>
          <helpaction>javax.help.BackAction</helpaction>
          <helpaction>javax.help.ForwardAction</helpaction>
          <helpaction>javax.help.SeparatorAction</helpaction>
          <helpaction>javax.help.HomeAction</helpaction>
          <helpaction>javax.help.ReloadAction</helpaction>
          <helpaction>javax.help.SeparatorAction</helpaction>
          <helpaction>javax.help.PrintAction</helpaction>
          <helpaction>javax.help.PrintSetupAction</helpaction>
		  <helpaction>javax.help.SeparatorAction</helpaction>
		  <helpaction>javax.help.FavoritesAction</helpaction>
       </toolbar>
    </presentation>
     <presentation displayviewimages="false">
         <name>main</name>
         <size width="640" height="480" />
         <location x="200" y="200" />
         <title>Statcato</title>
  </presentation>


</helpset>

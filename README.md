# Translatorium
Generic language files translator for  [Aronium](http://aronium.com) 

## Usage

java -jar translatorium.jar {langFrom} {langTo} {srcFile}

Where: 

- langFrom : source language  (of srcFile) ; 
- langTo: destination language
- srcFile: path of the source file, pick one from aronium/lang directory on your system

## How it works

This tool uses Google Translate via Google App Script to translate each sentence in the srcFile wich is just an XML file.

## Observation

To have a full translation of Aronium (including management interfaces) you have to translate all the required files. See this [documentation](https://help.aronium.com/hc/en-us/articles/115003595312-Translation) for more details on theses files. 



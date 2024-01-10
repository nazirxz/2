package com.example.justicia.model

import opennlp.tools.stemmer.PorterStemmer
import opennlp.tools.tokenize.SimpleTokenizer

class NLPProcessor {
    private val tokenizer = SimpleTokenizer.INSTANCE
    private val stemmer = PorterStemmer()

    fun tokenize(text: String): List<String> {
        return tokenizer.tokenize(text).toList()
    }

    fun stem(word: String): String {
        return stemmer.stem(word)
    }

    // Add other NLP-related functions as needed
}
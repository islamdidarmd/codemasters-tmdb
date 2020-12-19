package com.codemasters.tmdb.utils

fun String.toPosterImageUrl() = "${POSTER_BASE_URL}$this"
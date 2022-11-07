package com.example.delegadosapp

import java.util.Date

class PostElement(
    private val _uid: Int,
    private val datePost: Date,
    private val userPost: String,
    private val description: String,
    private val title: String,
    private val photo: String
) {
    override fun toString(): String {
        return "_uid=$_uid, datePost=$datePost, userPost='$userPost', description='$description', title='$title', photo='$photo'".replace(", ", ";").replace("=", ":")
    }

}
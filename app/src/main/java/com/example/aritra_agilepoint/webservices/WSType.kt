package com.example.aritra_agilepoint.webservices

import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class WSType {
    @IntDef(
        WSTypePref.GET,
        WSTypePref.POST,
        WSTypePref.PUT,
        WSTypePref.DELETE,
        WSTypePref.DOWNLOAD_FILE,
        WSTypePref.UPLOAD_FILE
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class WSTypePref {
        companion object {
            const val GET = 101
            const val POST = 102
            const val PUT = 103
            const val DELETE = 104
            const val DOWNLOAD_FILE = 105
            const val UPLOAD_FILE = 106
        }
    };
}
package org.bxkr.octodiary.models.marklistdate


import com.google.gson.annotations.SerializedName

data class Mark(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("comment_exists")
    val commentExists: Boolean,
    @SerializedName("control_form_name")
    val controlFormName: String,
    @SerializedName("created_at")
    val createdAt: String, // yyyy-MM-dd'T'HH:mm:ss
    @SerializedName("criteria")
    val criteria: Any?,
    @SerializedName("date")
    val lessonDate: String, // yyyy-MM-dd
    @SerializedName("id")
    val id: Long,
    @SerializedName("is_exam")
    val isExam: Boolean,
    @SerializedName("is_point")
    val isPoint: Boolean,
    @SerializedName("original_grade_system_type")
    val originalGradeSystemType: String,
    @SerializedName("point_date")
    val pointDate: Any?,
    @SerializedName("subject_id")
    val subjectId: Long,
    @SerializedName("subject_name")
    val subjectName: String,
    @SerializedName("updated_at")
    val updatedAt: String, // yyyy-MM-dd'T'HH:mm:ss
    @SerializedName("value")
    val value: String,
    @SerializedName("values")
    val values: Any?,
    @SerializedName("weight")
    val weight: Int
)
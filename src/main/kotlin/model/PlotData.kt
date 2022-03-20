package model

data class PlotData(
    val title: String,
    val xName: String,
    val yName: String,
    val data: Map<String, Any>
)

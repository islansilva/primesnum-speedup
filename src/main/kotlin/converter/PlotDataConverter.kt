package converter

import model.PlotData

fun Map<String, Any>.toPlotData(title: String, xName: String, yName: String) = PlotData(
    title = title,
    xName = xName,
    yName = yName,
    data = this
)
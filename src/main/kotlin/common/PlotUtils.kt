package common

import jetbrains.letsPlot.geom.geomPath
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.geom.geomSmooth
import jetbrains.letsPlot.geom.geomText
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import model.PlotData
import java.awt.Desktop
import java.io.File

object PlotUtils {

    fun createPlot(plotData: PlotData): Plot  =
        letsPlot(plotData.data)
            .plus(geomPoint(color = "dark-green", size = 4.0) { x = plotData.xName; y = plotData.yName })
            .plus(geomPath(color = "blue") { x = plotData.xName; y = plotData.yName })
            .plus(labs(title = plotData.title))

    fun createFile(content: String): File {
        val directory = File(System.getProperty("user.dir"), "lets-plot-images")
        directory.mkdir()

        val file = File(directory.canonicalPath, "speedup.html")
        file.createNewFile()
        file.writeText(content)

        return file
    }

    fun openInBrowser(file: File) = Desktop.getDesktop().browse(file.toURI())

}
package common

import jetbrains.datalore.plot.PlotSvgExport
import jetbrains.letsPlot.gggrid
import jetbrains.letsPlot.intern.Plot

class ExportSVG {

    companion object{
        fun buildSvgFromList(plots: List<Plot>): String = PlotSvgExport.buildSvgImageFromRawSpecs(
            gggrid(plots, 2, 700, 350).toSpec()
        )
    }
}
package com.example.branchapp.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.branchapp.databinding.FragmentHomeBinding
import com.example.ownerapp.di.components.DaggerFactoryComponent
import com.example.ownerapp.di.modules.FactoryModule
import com.example.ownerapp.di.modules.RepositoryModule
import com.example.ownerapp.mvvm.repository.MainRepository
import com.example.ownerapp.mvvm.viewmodles.MainViewModel
import org.eazegraph.lib.models.PieModel
import org.eazegraph.lib.models.ValueLinePoint

import org.eazegraph.lib.models.ValueLineSeries

import android.R

import org.eazegraph.lib.charts.ValueLineChart









class Home : Fragment() {
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        //Is branch Taken

        binding.pieChartHome.addPieSlice(
            PieModel(
                "Members", 80F,
                Color.parseColor("#66BB6A")
            )
        )
        binding.pieChartHome.addPieSlice(
            PieModel(
                "InActive", 20F,
                Color.parseColor("#FFA726")
            )
        )




        val series = ValueLineSeries()
        series.color = -0xa9480f

        series.addPoint(ValueLinePoint("Jan", 1000f))
        series.addPoint(ValueLinePoint("Feb", 3000f))
        series.addPoint(ValueLinePoint("Mar", 3200f))
        series.addPoint(ValueLinePoint("Apr", 6000f))
        series.addPoint(ValueLinePoint("Mai", 1000f))
        series.addPoint(ValueLinePoint("Jun", 3232f))
        series.addPoint(ValueLinePoint("Jul", 2478f))
        series.addPoint(ValueLinePoint("Aug", 1278f))
        series.addPoint(ValueLinePoint("Sep", 1000f))
        series.addPoint(ValueLinePoint("Oct", 3000f))
        series.addPoint(ValueLinePoint("Nov", 1000f))
        series.addPoint(ValueLinePoint("Dec", 3000f))

        binding.cubiclinechart.addSeries(series)
        binding.cubiclinechart.startAnimation()

        return binding.root
    }


    private fun init() {
        val component: DaggerFactoryComponent = DaggerFactoryComponent.builder()
            .repositoryModule(RepositoryModule(requireContext()))
            .factoryModule(FactoryModule(MainRepository(requireContext())))
            .build() as DaggerFactoryComponent
        viewModel =
            ViewModelProviders.of(this, component.getFactory()).get(MainViewModel::class.java)


    }


}
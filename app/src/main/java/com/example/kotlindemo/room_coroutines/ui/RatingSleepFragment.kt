package com.example.kotlindemo.room_coroutines.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.trackmysleepquality.sleepquality.SleepQualityViewModel
import com.example.android.trackmysleepquality.sleepquality.SleepQualityViewModelFactory

import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentRatingSleepBinding
import com.example.kotlindemo.room_coroutines.database.SleepDatabase


class RatingSleepFragment : Fragment() {

    private lateinit var ratingSleepBinding: FragmentRatingSleepBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        ratingSleepBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating_sleep, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = RatingSleepFragmentArgs.fromBundle(arguments!!)

        // Create an instance of the ViewModel Factory.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        val sleepQualityViewModel = ViewModelProviders.of(
                this, viewModelFactory).get(SleepQualityViewModel::class.java)

        // To use the View Model with data binding, you have to explicitly
        // give the binding object a reference to it.
        ratingSleepBinding.sleepQualityViewModel = sleepQualityViewModel

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
        sleepQualityViewModel.navigateToSleepTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                findNavController(this).navigate(
                    RatingSleepFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sleepQualityViewModel.doneNavigating()
            }
        })

        return ratingSleepBinding.root
    }

}

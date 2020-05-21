package com.example.kotlindemo.room_coroutines.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FragmentScheduleSleepBinding
import com.example.kotlindemo.room_coroutines.database.SleepDatabase
import com.example.kotlindemo.room_coroutines.viewmodel.SleepTrackerViewModel
import com.example.kotlindemo.room_coroutines.viewmodel.SleepTrackerViewModelFactory
import com.google.android.material.snackbar.Snackbar


class ScheduleSleepFragment : Fragment() {

    private lateinit var sleepBinding: FragmentScheduleSleepBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sleepBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_sleep, container, false)
//        sleepBinding.apply {
//
//        }
        //Or Different Way to initialize
        //No need for mentioning layout
        //sleepBinding = FragmentScheduleSleepBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SleepTrackerViewModel::class.java)
        sleepBinding.sleepTrackerViewModel = sleepTrackerViewModel
        sleepBinding.setLifecycleOwner(viewLifecycleOwner)

//        sleepBinding.sleepTrackerViewModel.tonight.observe(viewLifecycleOwner, Observer {
//            var sleepNight: SleepNight? = it
//            println("Sleep NIght: " + sleepNight?.startTime)
//            println("Sleep NIght: " + sleepNight?.sleepId)
//        })

        sleepTrackerViewModel.navigateToSleepQuality.observe(viewLifecycleOwner, Observer { night1 ->
            night1?.let {
                //This night1 is the object value that we set in onStopTracking in ViewModel

                findNavController(this).navigate(
                    ScheduleSleepFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment(night1.sleepId))
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                sleepTrackerViewModel.doneNavigating()
            }
        })

        sleepTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true){
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                sleepTrackerViewModel.doneSnackBarShowing()
            }
        })

        return sleepBinding.root
    }

}

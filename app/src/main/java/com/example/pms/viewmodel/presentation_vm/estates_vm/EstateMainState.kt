package com.example.pms.viewmodel.presentation_vm.estates_vm

import com.example.pms.viewmodel.destinations.Destination

data class EstateMainState(

    var currentPageRoute: String = Destination.EstatesHomeDestination.route

)
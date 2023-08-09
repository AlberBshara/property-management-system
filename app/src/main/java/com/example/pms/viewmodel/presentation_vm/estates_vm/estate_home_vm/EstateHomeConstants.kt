package com.example.pms.viewmodel.presentation_vm.estates_vm.estate_home_vm

import com.example.pms.R

class EstateHomeConstants {


    companion object {


        data class FilteringType(
            val title: Int,
            val items: List<Int>
        )

        val listOfFiltering = listOf(

            FilteringType(
                title = R.string.Location, items = listOf(
                    R.string.Damascus,
                    R.string.Aleppo,
                    R.string.Homs,
                    R.string.RifDimashq,
                    R.string.Latakia,
                    R.string.Idlib,
                    R.string.Ḥamah,
                    R.string.DayralZawr,
                    R.string.Daraa,
                    R.string.AlSuwayda,
                    R.string.AlRaqqah,
                    R.string.AlQunayṭirah,
                    R.string.AlḤasakah
                )
            ),

            FilteringType(
                title = R.string.estate_type, items = listOf(
                    R.string.land,
                    R.string.apartment,
                    R.string.villa,
                    R.string.commercialShops,
                    R.string.warehouses,
                    R.string.commercialRealEstate
                )
            ),

            FilteringType(
                title = R.string.Operation_type,
                items = listOf(R.string.sellEstate, R.string.rent)
            ),

            FilteringType(
                title = R.string.status_type,
                items = listOf(R.string.furnished, R.string.barebones)
            ),

            FilteringType(title = R.string.Price_down_to, items = listOf(R.string.zero)),
            FilteringType(title = R.string.Price_up_to, items = listOf(R.string.zero)),

            FilteringType(title = R.string.Space_down_to, items = listOf(R.string.one)),
            FilteringType(title = R.string.Space_up_to, items = listOf(R.string.one)),
            FilteringType(title = R.string.Level_down_to, items = listOf(R.string.one)),
            FilteringType(title = R.string.Level_up_to, items = listOf(R.string.one))
        )

        val listOfPrice = listOf(500, 1000, 2000, 5000, 10000, 20000, 50000, 100000, 500000)
        val listOfSpace = listOf(50, 80, 100, 150, 200, 250, 300, 500, 1000, 2000, 5000, 10000)
        val listOfLevel = listOf(1, 5, 10, 15, 20, 25, 30, 35, 40, 50, 80, 100)
    }


}
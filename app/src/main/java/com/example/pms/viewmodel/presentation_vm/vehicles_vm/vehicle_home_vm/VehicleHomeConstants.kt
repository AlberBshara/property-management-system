package com.example.pms.viewmodel.presentation_vm.vehicles_vm.vehicle_home_vm

class VehicleHomeConstants {

    companion object {

        val listOfFiltering = listOf (
            FilteringType(title = FilteringTitles.All, items = listOf("All")),
            FilteringType(title = FilteringTitles.Color, items = listOf("Red", "Blue")),
            FilteringType(title = FilteringTitles.Price , items = listOf("less than 1000", "more tha 1000")),
            FilteringType(title = FilteringTitles.Transmission, items = listOf("manual", "automatic")),
            FilteringType(title = FilteringTitles.Body, items = listOf("Truck", "small car")),
            FilteringType(title = FilteringTitles.Year, items = listOf("2002", "2003", "2004")),
            FilteringType(title = FilteringTitles.Used , items = listOf("Need some repairs", "good case")),
            FilteringType(title = FilteringTitles.New, items = listOf("new this year", "Showroom car")),
            FilteringType(title = FilteringTitles.Fuel , items = listOf("gasoline","electricity")),
            FilteringType(title = FilteringTitles.Model, items = listOf("BMW", "Honda")),
            FilteringType(title = FilteringTitles.Mileage , items = listOf("1000km", "2000km")),
            FilteringType(title = FilteringTitles.Location, items = listOf("Homs", "Damascs"))
        )

        enum class FilteringTitles {
            All, Color , Price ,
            Transmission , Body,
            Year , Used , New ,
            Fuel , Model , Mileage ,
            Location
        }

        data class FilteringType(
            val title : FilteringTitles,
            val items : List<String>
        )

    }
}
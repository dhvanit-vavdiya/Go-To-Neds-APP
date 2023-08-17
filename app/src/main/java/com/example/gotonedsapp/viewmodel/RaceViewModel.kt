package com.example.gotonedsapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotonedsapp.apiservices.ApiService
import com.example.gotonedsapp.data.AdvertisedStart
import com.example.gotonedsapp.data.Data
import com.example.gotonedsapp.data.RaceData
import com.example.gotonedsapp.data.RaceSummaries
import com.example.gotonedsapp.utils.Constants
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RaceViewModel: ViewModel() {
    var errorMessage: String by mutableStateOf("")

    private var _raceData = mutableStateListOf<RaceData>()
    var raceData: List<RaceData>
        get() = _raceData
        set(value) {}

    private var _raceSummaries: MutableList<RaceSummaries> = ArrayList()
    var raceSummaries: List<RaceSummaries> = ArrayList()
        get() = _raceSummaries

    private var _greyhoundRaceSummaries: MutableList<RaceSummaries> = ArrayList()
    var greyhoundRaceSummaries: List<RaceSummaries> = ArrayList()
        get() = _greyhoundRaceSummaries

    private var _harnessRaceSummaries: MutableList<RaceSummaries> = ArrayList()
    var harnessRaceSummaries: List<RaceSummaries> = ArrayList()
        get() = _harnessRaceSummaries

    private var _horseRaceSummaries: MutableList<RaceSummaries> = ArrayList()
    var horseRaceSummaries: List<RaceSummaries> = ArrayList()
        get() = _horseRaceSummaries

    fun getTodoList() {

        viewModelScope.launch {

            val apiService = ApiService.getInstance()

            try {
                val call: Call<JsonObject> = apiService.getData("nextraces", 10)

                _raceData.clear()

                call!!.enqueue(object : Callback<JsonObject?> {
                    override fun onResponse(
                        call: Call<JsonObject?>,
                        response: Response<JsonObject?>
                    ) {
                        if (response.isSuccessful) {

                            val jsonObj: JsonObject
                            val status: Int
                            val message: String

                            try {
                                if (response.body()!!.get("data") != null) {

                                    jsonObj = response.body()!!.get("data").asJsonObject
                                    status = response.body()!!.get("status").asInt
                                    message = response.body()!!.get("message").asString

                                    println("get response jsonObj:$jsonObj")

                                    val jsonArrayNextToGo: JsonArray =
                                        jsonObj.getAsJsonArray("next_to_go_ids")
                                    println("get response jsonArrayNextToGo:$jsonArrayNextToGo")

                                    val nextToGoItem: MutableList<String> = ArrayList()

                                    for (i in 0 until jsonArrayNextToGo.size()) {

                                        //raceData = listOf(RaceData( data1, "get data", 200))
                                        nextToGoItem.add(jsonArrayNextToGo.get(i).asString)
                                    }
                                    println("get NextToGoItems$nextToGoItem")

                                    val jsonRaceSummaries: JsonObject =
                                        jsonObj.get("race_summaries").asJsonObject
                                    println("get response jsonRaceSummaries:$jsonRaceSummaries")

                                    var race: RaceSummaries? = null

                                    for (i in 0 until nextToGoItem.size) {

                                        if (jsonRaceSummaries.has(nextToGoItem[i])) {

                                            val jsonRaceID: JsonObject =
                                                jsonRaceSummaries.get(nextToGoItem[i]).asJsonObject

                                            val jsonAdvertised: JsonObject =
                                                jsonRaceID.get("advertised_start").asJsonObject

                                            race = RaceSummaries(
                                                AdvertisedStart(jsonAdvertised.get("seconds").asInt),
                                                jsonRaceID.get("category_id").asString,
                                                jsonRaceID.get("meeting_id").asString,
                                                jsonRaceID.get("meeting_name").asString,
                                                jsonRaceID.get("race_id").asString,
                                                jsonRaceID.get("race_name").asString,
                                                jsonRaceID.get("race_number").asInt,
                                                jsonRaceID.get("venue_country").asString,
                                                jsonRaceID.get("venue_id").asString,
                                                jsonRaceID.get("venue_name").asString,
                                                jsonRaceID.get("venue_state").asString
                                            )
                                        }

                                        if (race != null) {

                                            when (race.category_id) {

                                                Constants.GREYHOUND -> {
                                                    _greyhoundRaceSummaries.add(race)
                                                }
                                                Constants.HARNESS -> {
                                                    _harnessRaceSummaries.add(race)
                                                }
                                                else -> {
                                                    _horseRaceSummaries.add(race)
                                                }
                                            }

                                            _raceSummaries.add(race)
                                        }
                                    }

                                    println("get RaceData: $_raceSummaries")

                                    raceSummaries = _raceSummaries

                                    val data = Data(nextToGoItem, raceSummaries)
                                    _raceData = mutableStateListOf((RaceData(data, message, status)).copy())

                                    println("get all data$raceSummaries")
                                }

                            } catch (exception: Exception) {
                                println("get exception:$exception")
                            }
                        }
                    }

                    override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                        // displaying an error message in toast
                        if (t != null) {
                            println("get response error:" + t.message)
                        }
                    }
                })

            } catch (e: Exception) {

                errorMessage = e.message.toString()
            }
        }
    }

    fun setSortByCategory(label: String): List<RaceSummaries> {

        println("get race sort::"+label)

       return when (label) {

            Constants.greyHound -> {
                println("get race sort:: 1")
                greyhoundRaceSummaries
            }

            Constants.harness -> {
                println("get race sort:: 2")
                harnessRaceSummaries
            }

            Constants.horse -> {
                println("get race sort:: 3")
                horseRaceSummaries
            }

            else -> {
                println("get race sort:: 4")
                raceSummaries
            }
        }
    }
}
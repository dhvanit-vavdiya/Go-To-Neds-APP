package com.example.gotonedsapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.gotonedsapp.R
import com.example.gotonedsapp.data.RaceSummaries
import com.example.gotonedsapp.ui.theme.GoToNedsAppTheme
import com.example.gotonedsapp.utils.Constants
import com.example.gotonedsapp.viewmodel.RaceViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = RaceViewModel()

        setContent {
            println("3")
            GoToNedsAppTheme {
                TodoView(viewModel)
            }

        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TodoView(viewModel: RaceViewModel) {

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit, block = {

        viewModel.getTodoList()
    })

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a list of race categories
    val mCategories = listOf("ALL", "Greyhound", "Harness", "Horse")

    // Create a string value to store the selected category
    var mSelectedText by remember { mutableStateOf(mCategories[0]) }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var raceData: List<RaceSummaries> = viewModel.raceSummaries
    //var raceData = mutableListOf<RaceSummaries>()
    //raceData = viewModel.raceSummaries as MutableList<RaceSummaries>

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    Row {
                        Text(
                            text = "Neds",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                            )
                    }
                })
        },
        content = {
            if (viewModel.errorMessage.isEmpty()) {

                println("get content")

                Column(modifier = Modifier.padding(16.dp)) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(

                            value = mSelectedText,
                            onValueChange = { mSelectedText = it },
                            modifier = Modifier
                                .wrapContentSize()
                                .onGloballyPositioned { coordinates ->
                                    // This value is used to assign to
                                    // the DropDown the same width
                                    mTextFieldSize = coordinates.size.toSize()
                                },
                            label = {
                                Text(
                                    text ="Sort Category:",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            },
                            trailingIcon = {
                                Icon(icon, "contentDescription",
                                    Modifier.clickable { mExpanded = !mExpanded })
                            }
                        )

                        // Create a drop-down menu with list of cities,
                        // when clicked, set the Text Field text as the city selected
                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier
                                .width(with(LocalDensity.current) {
                                    mTextFieldSize.width.toDp()
                                })
                        ) {
                            mCategories.forEach { label -> DropdownMenuItem(onClick = {

                                println("get race sort data" + raceData)

                                mSelectedText = label

                                coroutineScope.launch {
                                    // Perform any asynchronous actions if needed
                                    // For example, navigate to another screen
                                    raceData = emptyList()
                                    println("get race sort data " + raceData)
                                    raceData = when (label) {

                                        Constants.greyHound -> {
                                            println("get race sort:: 1 1")
                                            println("get race :" + viewModel.setSortByCategory(label).size)
                                            viewModel.setSortByCategory(label)
                                        }
                                        Constants.harness -> {
                                            println("get race sort:: 1 2")
                                            println("get race :" + viewModel.setSortByCategory(label).size)
                                            viewModel.setSortByCategory(label)
                                        }
                                        Constants.horse -> {
                                            println("get race sort:: 1 3")
                                            println("get race :" + viewModel.setSortByCategory(label).size)
                                            viewModel.setSortByCategory(label)
                                        }
                                        else -> {
                                            println("get race sort:: 1 44")
                                            println("get race :" + viewModel.setSortByCategory(label).size)
                                            viewModel.setSortByCategory(label)
                                        }
                                    }
                                    println("get race sort data " + raceData)
                                }

                                mExpanded = false

                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }

                    LazyColumn(modifier = Modifier.fillMaxHeight()) {

                        items(raceData) { todo ->

                            Card(
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 4.dp,
                                        end = 4.dp,
                                        top = 4.dp
                                    )
                                    .fillMaxWidth()

                                /*.clickable(onClick = {

                                        var intent = Intent(context, DetailActivity::class.java)
                                                intent.putExtra("student", item)
                            context.startActivity(intent)

                        })*/
                            ) {

                                //Set CountDown Timer
                                println("get time   " + todo.advertised_start.seconds)
                                println("get time calender " + Calendar.getInstance().timeInMillis)

                                val time = (todo.advertised_start.seconds).minus(Calendar.getInstance().timeInMillis)
                                var timeLeft by remember { mutableStateOf(time) }

                                LaunchedEffect(key1 = timeLeft) {
                                    while (timeLeft > 0) {
                                        delay(1000L)
                                        timeLeft--
                                    }
                                }

                                val secMilSec: Long = 1000
                                val minMilSec = 60 * secMilSec
                                val hourMilSec = 60 * minMilSec
                                val dayMilSec = 24 * hourMilSec

                                val hours = (time % dayMilSec / hourMilSec).toInt()
                                val minutes = (time % dayMilSec % hourMilSec / minMilSec).toInt()
                                val seconds = (time % dayMilSec % hourMilSec % minMilSec / secMilSec).toInt()

                                //Set category of Horse, Harness and Greyhound
                                val categoryImage: Int = when (todo.category_id) {
                                    Constants.GREYHOUND -> {
                                        R.drawable.greyhound
                                    }
                                    Constants.HARNESS -> {
                                        R.drawable.harness
                                    }
                                    else -> {
                                        R.drawable.horse
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                )
                                {
                                    Image(
                                        modifier = Modifier
                                            .height(40.dp)
                                            .width(40.dp)
                                            .padding(0.dp, 0.dp, 4.dp, 0.dp),
                                        painter = painterResource(categoryImage),
                                        contentDescription = null,
                                        alignment = Alignment.Center
                                    )
                                    Text(
                                        text = todo.meeting_name + " " + todo.race_number ,
                                        maxLines = 1,
                                        modifier = Modifier.weight(1F),
                                        textAlign = TextAlign.Left,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = String.format(" %02d:%02d:%02d", hours, minutes, seconds),
                                        maxLines = 1,
                                        modifier = Modifier
                                            .padding(6.dp, 0.dp, 0.dp, 0.dp),
                                        textAlign = TextAlign.Center,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Image(
                                        modifier = Modifier
                                            .height(30.dp)
                                            .width(30.dp)
                                            .padding(2.dp, 2.dp, 2.dp, 2.dp),
                                        painter = painterResource(R.drawable.baseline_navigate_next_24),
                                        contentDescription = null,
                                        alignment = Alignment.Center
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))
                                }
                                Divider()
                            }
                        }
                    }
                }
            } else {
                Text(viewModel.errorMessage)
            }
        }
    )
}
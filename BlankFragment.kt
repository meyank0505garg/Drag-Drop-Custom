@file:OptIn(ExperimentalLayoutApi::class)

package com.example.testingcompose.composepck

import android.location.Location
import android.location.LocationListener
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.digitaldukaan.models.response.AddProductImagesResponse
import com.digitaldukaan.models.response.AttributesItem
import com.digitaldukaan.models.response.VariantNameItem
import com.digitaldukaan.models.response.VariantOptionItem
import com.example.testingcompose.databinding.FragmentBlankBinding
import com.example.testingcompose.dragAndDrop.DraggableItem
import com.example.testingcompose.dragAndDrop.DraggableSection
import com.example.testingcompose.intentLaunching.CustomGallaryContact
import com.example.testingcompose.stableDS.ImmutableObject
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class BlankFragment : Fragment(),
    LocationListener {

    companion object {

        fun newInstance() = BlankFragment()
    }

    private lateinit var viewModel: BlankViewModel
    private lateinit var binding: FragmentBlankBinding

    private val TAG = "BlankFrag"

    private val intentResultLauncher = registerForActivityResult(CustomGallaryContact()) { imageUri ->

        Log.d(TAG, "intentResultLauncher: $imageUri ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(BlankViewModel::class.java)

        binding = FragmentBlankBinding.inflate(inflater, container, false)

        val mContentView = binding.root


        return mContentView
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AddProductFragmentScreen(viewModelObject = ImmutableObject(viewModel))

            }

        }
    }

    override fun onLocationChanged(location: Location) {
    }
}

private val TAG = "BlankFrag"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductFragmentScreen(viewModelObject: ImmutableObject<BlankViewModel>) {
    val scrollState = rememberScrollState()
    val viewModel = viewModelObject.data
    var showSaveNavButton by remember {
        mutableStateOf(false)
    }
    val staticText = viewModel.addProductResponse?.addProductStaticText
    Scaffold(topBar = {

    }) {
        Column(
                Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(it)
                    .background(Color(247, 248, 248))
        ) {

            PoductInfoSection(
                    viewModelObject = viewModelObject,
                    sectionHeading = staticText?.text_product_info ?: "Product Info",
                    productNameLabel = staticText?.hint_item_name ?: "Product Name"
            )

        }

    }
}

@Composable
fun HeadingContent(
    sectionHeading: String,
    isFeatureLocked: Boolean,
) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
                text = sectionHeading,
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp, 19.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))

        if (isFeatureLocked) {
            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Feature is Locked",
                        modifier = Modifier.size(18.dp)
                )
                TextButton(
                        onClick = { /*TODO*/ },
                        contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                            text = "Unlock Now",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(15.dp, 19.dp)
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoductInfoSection(
    sectionHeading: String,
    productNameLabel: String,
    viewModelObject: ImmutableObject<BlankViewModel>,

    ) {
    var productName by remember {
        mutableStateOf("")
    }
    Column(
            modifier = Modifier.fillMaxWidth()
    ) {

        HeadingContent(sectionHeading = sectionHeading, isFeatureLocked = false)
        Card(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .padding(10.dp, 5.dp)
        ) {
//                Product Name input field
            OutlinedTextField(
                    value = productName,
                    onValueChange = {
                        productName = it
                        viewModelObject.data.productName = it
                    },
                    label = {
//                        static data -> hint_item_name
                        Text(text = productNameLabel)
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                            fontSize = 15.sp, color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .padding(9.dp)
                        .fillMaxWidth(),
            )
            DraggableSection(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
            ) {
                AddProductMediaImages(viewModelObject)
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductMediaImages(viewModelObject: ImmutableObject<BlankViewModel>) {

//    TODO instead of providing source key and destination key..on Drag ended provide both

//    TODO make it by remember by somehow
    val imageList by remember(viewModelObject.data.imgagesList) {
        derivedStateOf { viewModelObject.data.imgagesList.toList() }
    }

    val gridState = rememberLazyGridState()
    var sourceKey by remember {
        mutableStateOf(-1L)
    }
    var destKey by remember {
        mutableStateOf(-1L)
    }

    Surface(
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            shape = RoundedCornerShape(9.dp)
    ) {
        Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()

        ) {

            DraggableItem(itemKey = imageList[0].imageId,
                    destinationKey = { destKey = it },
                    sourceKey = { sourceKey = it }) {
                ListItems(
                        item = imageList[0],
                        position = 0,
                        onItemClicked = {
                            TODO("")
                        },
                        modifier = Modifier.size(130.dp),
                        destKey
                )
            }

            Column(
                    modifier = Modifier
                        .width(170.dp)
                        .height(170.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(
                        columns = GridCells.FixedSize(70.dp),
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        state = gridState,

                        ) {
                    val filteredList = imageList.subList(1, imageList.size)
                    itemsIndexed(filteredList, key = { index, item ->
                        item.imageId
                    }) { index, item ->

                        DraggableItem(itemKey = item.imageId,
                                destinationKey = { destKey = it },
                                sourceKey = { sourceKey = it }) {
                            ListItems(
                                    item = item,
                                    position = index + 1,
                                    modifier = Modifier.size(70.dp),
                                    onItemClicked = {
                                        TODO("")
                                    },
                                    imageIId = destKey

                            )
                        }

                    }
                }

            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItems(
    item: AddProductImagesResponse,
    position: Int,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
    imageIId: Long

) {
    Card(modifier = modifier
        .padding(3.dp)
        .border(1.dp, if (imageIId == item.imageId) randomColor() else Color.Blue),

            onClick = {
                onItemClicked(position)
            }

    ) {
        Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
        ) {
            if (item.imageUrl.isEmpty()) {

                Text(text = "${item.imageId}")
            } else {
                AsyncImage(
                        model = item.imageUrl, contentDescription = "Image url"
                )
            }
        }

    }
}

fun randomColor() = Color(
        Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), alpha = 255
)

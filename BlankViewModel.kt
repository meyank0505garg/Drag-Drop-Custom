package com.example.testingcompose.composepck

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitaldukaan.models.response.AddProductImagesResponse
import com.digitaldukaan.models.response.AddProductResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

 class BlankViewModel @Inject constructor() : ViewModel() {

    // TODO: Implement the ViewModel
    var productName : String = ""
    val imgagesList = mutableStateListOf<AddProductImagesResponse>().apply {
        add(AddProductImagesResponse(imageId = 0))
        add(AddProductImagesResponse(imageId = 1))
        add(AddProductImagesResponse(imageId = 2))
        add(AddProductImagesResponse(imageId = 3))
        add(AddProductImagesResponse(imageId = 4))
    }

     var addProductResponse : AddProductResponse? = null

    var counter: Int = 0

    var sourceId: Long? = null
    var destId: Long? = null

    init {
//        TODO after api response..make imgagesList to size of 5.


    }

    fun setDestId(id:Any){
        destId = id as Long
//        Log.d("DragCheck", "setDestId: $destId ")

    }

    fun setSourceId(id:Any){
        sourceId = id as Long

//        Log.d("DragCheck", "setSourceId: $sourceId")

    }


    fun startDraging(id: Any) {
        setSourceId(id)


    }

    fun dragEnd(){

    }

    fun dragCancel(){

    }

    fun updateTEmpList() {
//        tempList.value = ArrayList<Pair<Int,String>>().apply {
//            add(Pair(10,"Third"))
//        }
    }

    fun uploadImageTest(
        position: Int = 0
    ) {
        viewModelScope.launch {

            val item = imgagesList[position]


            imgagesList[position] = item.copy(imageUrl = "https://cdn.dotpe.in/longtail/collection/compressed/5875/ySYL9I8k.webp", imageId = counter.toLong())
            counter++

        }
    }

    fun printImgList() {

        imgagesList.forEach {
//            Log.d("DragChecking", "printImgList: ${it} ")
        }
    }
}
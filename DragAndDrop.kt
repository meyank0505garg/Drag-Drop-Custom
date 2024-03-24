package com.digitaldukaan.dragAndDrop


import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned

internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

internal class DragTargetInfo {

    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
}

@Composable
fun DraggableSection(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit)
) {
    val state = remember { DragTargetInfo() }
    var screenPositionRelativeToWindow by remember {
        mutableStateOf(Offset.Zero)
    }
    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                screenPositionRelativeToWindow = it.localToWindow(Offset.Zero)
            })
        {
            content()
            if (state.isDragging) {
                Box(modifier = Modifier
                    .graphicsLayer {

                        val offset = (state.dragPosition + state.dragOffset)
                        scaleX = 0.8f
                        scaleY = 0.8f
                        alpha = .7f
                        translationX = offset.x - screenPositionRelativeToWindow.x
                        translationY = offset.y - screenPositionRelativeToWindow.y

                    }
                    .background(Color.Black)
                ) {
                    state.draggableComposable?.let { it() }
                }
            }
        }
    }
}

@Composable
fun <E> DraggableItem(
    modifier: Modifier = Modifier,
    itemKey: E,
    destinationKey: (E) -> Unit,
    sourceKey: (E) -> Unit,
    content: @Composable (() -> Unit),

    ) {
    var currentTargetPositionRelativeToWindow by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current
    val dragPosition = currentState.dragPosition
    val dragOffset = currentState.dragOffset
    var isCurrentDropTarget by remember {
        mutableStateOf(false)
    }
    Box(modifier = modifier
        .onGloballyPositioned {
            currentTargetPositionRelativeToWindow = it.localToWindow(
                Offset.Zero
            )
            it
                .boundsInWindow()
                .let { rect ->
                    isCurrentDropTarget = rect.contains(dragPosition + dragOffset)
                }
        }
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
                onDragStart = { positionOfPointerRelativeToTargetBoundaries ->
                    currentState.isDragging = true
                    currentState.dragPosition = currentTargetPositionRelativeToWindow + positionOfPointerRelativeToTargetBoundaries
                    currentState.draggableComposable = content
                    sourceKey(itemKey)

                }, onDrag = { change, dragAmount ->
                    change.consume()
                    currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)

                }, onDragEnd = {
                    currentState.isDragging = false
                    currentState.dragOffset = Offset.Zero
                    isCurrentDropTarget = false

                }, onDragCancel = {
                    currentState.dragOffset = Offset.Zero
                    currentState.isDragging = false
                    isCurrentDropTarget = false
                })
        }

    ) {
        content()
        if (isCurrentDropTarget && currentState.isDragging) {
            destinationKey(itemKey)
        }

    }
}


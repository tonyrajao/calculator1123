package com.example.calculatrice

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Palette de couleurs
private val BackgroundBlack = Color(0xFF000000)
private val DarkGray = Color(0xFF333333)
private val LightGray = Color(0xFFA5A5A5)
private val Orange = Color(0xFFFF9F0A)
private val White = Color(0xFFFFFFFF)

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel = viewModel()
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    
    Surface(
        modifier = modifier.fillMaxSize(),
        color = BackgroundBlack
    ) {
        if (isPortrait) {
            PortraitCalculatorLayout(viewModel = viewModel)
        } else {
            LandscapeCalculatorLayout(viewModel = viewModel)
        }
    }
}

@Composable
fun PortraitCalculatorLayout(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DisplayScreen(
            text = viewModel.displayText,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            viewModel = viewModel
        )
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton(
                    text = "C",
                    onClick = { viewModel.clear() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = LightGray,
                    contentColor = BackgroundBlack
                )
                CalculatorButton(
                    text = "⌫",
                    onClick = { viewModel.onDeleteClick() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = LightGray,
                    contentColor = BackgroundBlack
                )
                CalculatorButton(
                    text = "%",
                    onClick = { viewModel.onOperationClick(CalculatorViewModel.Operation.MODULO) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = LightGray,
                    contentColor = BackgroundBlack
                )
                CalculatorButton(
                    text = "÷",
                    onClick = { viewModel.onOperationClick(CalculatorViewModel.Operation.DIVIDE) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Orange,
                    contentColor = White
                )
            }
            
            // Ligne 2: 7, 8, 9, ×
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton("7", { viewModel.onNumberClick("7") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton("8", { viewModel.onNumberClick("8") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton("9", { viewModel.onNumberClick("9") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton(
                    text = "×",
                    onClick = { viewModel.onOperationClick(CalculatorViewModel.Operation.MULTIPLY) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Orange,
                    contentColor = White
                )
            }
            
            // Ligne 3: 4, 5, 6, -
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton("4", { viewModel.onNumberClick("4") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton("5", { viewModel.onNumberClick("5") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton("6", { viewModel.onNumberClick("6") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton(
                    text = "-",
                    onClick = { viewModel.onOperationClick(CalculatorViewModel.Operation.SUBTRACT) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Orange,
                    contentColor = White
                )
            }
            
            // Ligne 4: 1, 2, 3, +
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton("1", { viewModel.onNumberClick("1") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton("2", { viewModel.onNumberClick("2") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton("3", { viewModel.onNumberClick("3") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton(
                    text = "+",
                    onClick = { viewModel.onOperationClick(CalculatorViewModel.Operation.ADD) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Orange,
                    contentColor = White
                )
            }
            
            // Ligne 5: +/-, 0, ., =
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CalculatorButton(
                    text = "+/-",
                    onClick = { viewModel.onNegateClick() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = LightGray,
                    contentColor = BackgroundBlack
                )
                CalculatorButton("0", { viewModel.onNumberClick("0") }, Modifier.weight(1f), DarkGray, White)
                CalculatorButton(
                    text = ".",
                    onClick = { viewModel.onDecimalClick() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = DarkGray,
                    contentColor = White
                )
                CalculatorButton(
                    text = "=",
                    onClick = { viewModel.onEqualsClick() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Orange,
                    contentColor = White
                )
            }
        }
    }
}

@Composable
fun LandscapeCalculatorLayout(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LandscapeDisplayScreen(
            text = viewModel.displayText,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            viewModel = viewModel
        )
        
        // Grille de boutons en bas
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Ligne 1: C, ⌫, %, ÷
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LandscapeCalculatorButton("C", { viewModel.clear() }, Modifier.weight(1f), LightGray, BackgroundBlack)
                LandscapeCalculatorButton("⌫", { viewModel.onDeleteClick() }, Modifier.weight(1f), LightGray, BackgroundBlack)
                LandscapeCalculatorButton("%", { viewModel.onOperationClick(CalculatorViewModel.Operation.MODULO) }, Modifier.weight(1f), LightGray, BackgroundBlack)
                LandscapeCalculatorButton("÷", { viewModel.onOperationClick(CalculatorViewModel.Operation.DIVIDE) }, Modifier.weight(1f), Orange, White)
            }
            
            // Ligne 2: 7, 8, 9, ×
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LandscapeCalculatorButton("7", { viewModel.onNumberClick("7") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("8", { viewModel.onNumberClick("8") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("9", { viewModel.onNumberClick("9") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("×", { viewModel.onOperationClick(CalculatorViewModel.Operation.MULTIPLY) }, Modifier.weight(1f), Orange, White)
            }
            
            // Ligne 3: 4, 5, 6, -
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LandscapeCalculatorButton("4", { viewModel.onNumberClick("4") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("5", { viewModel.onNumberClick("5") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("6", { viewModel.onNumberClick("6") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("-", { viewModel.onOperationClick(CalculatorViewModel.Operation.SUBTRACT) }, Modifier.weight(1f), Orange, White)
            }
            
            // Ligne 4: 1, 2, 3, +
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LandscapeCalculatorButton("1", { viewModel.onNumberClick("1") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("2", { viewModel.onNumberClick("2") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("3", { viewModel.onNumberClick("3") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("+", { viewModel.onOperationClick(CalculatorViewModel.Operation.ADD) }, Modifier.weight(1f), Orange, White)
            }
            
            // Ligne 5: +/-, 0, ., =
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LandscapeCalculatorButton("+/-", { viewModel.onNegateClick() }, Modifier.weight(1f), LightGray, BackgroundBlack)
                LandscapeCalculatorButton("0", { viewModel.onNumberClick("0") }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton(".", { viewModel.onDecimalClick() }, Modifier.weight(1f), DarkGray, White)
                LandscapeCalculatorButton("=", { viewModel.onEqualsClick() }, Modifier.weight(1f), Orange, White)
            }
        }
    }
}

@Composable
fun DisplayScreen(
    text: String,
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    LaunchedEffect(text) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Calculator Result", viewModel.getDisplayTextForClipboard())
                        clipboard.setPrimaryClip(clip)
                    }
                ) {
                    Text("Copier", fontSize = 14.sp, color = LightGray)
                }
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = text,
                    fontSize = 64.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, bottom = 16.dp),
                    color = White,
                    fontWeight = FontWeight.W300,
                    lineHeight = 72.sp
                )
            }
        }
    }
}

@Composable
fun LandscapeDisplayScreen(
    text: String,
    modifier: Modifier = Modifier,
    viewModel: CalculatorViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    LaunchedEffect(text) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Calculator Result", viewModel.getDisplayTextForClipboard())
                        clipboard.setPrimaryClip(clip)
                    }
                ) {
                    Text("Copier", fontSize = 14.sp, color = LightGray)
                }
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = text,
                    fontSize = 48.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, bottom = 16.dp),
                    color = White,
                    fontWeight = FontWeight.W300,
                    lineHeight = 56.sp
                )
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = DarkGray,
    contentColor: Color = White
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(68.dp)
            .aspectRatio(1f),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(50),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400
        )
    }
}

@Composable
fun LandscapeCalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = DarkGray,
    contentColor: Color = White
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(22.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400
        )
    }
}

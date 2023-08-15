package com.example.blogapp.feature_profile.presentation.change_user_data_presentation.comp

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.blogapp.core.comp.textfield.TextFieldStandard
import com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.PostCreateEditEvent
import com.example.blogapp.feature_blog.presentation.post_create_edit_presentation.comp.PhotoPresent
import com.example.blogapp.feature_profile.presentation.change_email_presentation.ChangeEmailEvent
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.ChangeUserDataEvent
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.ChangeUserDataUiEvent
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.ChangeUserDataViewModel
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.Gender
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.Title
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.date_time.DateTimeDialog
import com.maxkeppeler.sheets.date_time.models.DateTimeSelection
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Composable
fun ChangeUserDataPresentation(
    navHostController: NavHostController,
    viewModel: ChangeUserDataViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val dataPickerState = rememberUseCaseState()

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            viewModel.onEvent(ChangeUserDataEvent.SetImage(it))
        }
    )

    DateTimeDialog(
        state = dataPickerState,
        selection = DateTimeSelection.Date {
            viewModel.onEvent(ChangeUserDataEvent.EnteredDateOfBirthDay(it))
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.sharedFlow.collectLatest { event ->
            when (event) {
                ChangeUserDataUiEvent.Save -> {
                    navHostController.popBackStack()
                }
                is ChangeUserDataUiEvent.Toast -> {
                    Toast.makeText(context, event.value, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.onEvent(ChangeUserDataEvent.SaveUserProfile)
                    },
                    shape = CircleShape,
                    contentColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Change user data",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    PhotoPresent(
                        uri = state.imagePath,
                        imageLink = state.imageUrl,
                        icon = Icons.Outlined.AccountCircle,
                        onClick = {
                            singlePhotoPicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        errorMessage = null
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                }

                item {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                    ) {
                        Title.values().forEach {
                            OptionPresent(
                                isChosen = state.title == it,
                                value = it.name + "."
                            ) {
                                viewModel.onEvent(ChangeUserDataEvent.ChooseTitleOption(it))
                            }
                        }
                    }

                    TextFieldStandard(
                        label = "First name",
                        value = state.firstName,
                        onValueChange = {
                            viewModel.onEvent(ChangeUserDataEvent.EnteredFirstName(it))
                        },
                        errorMessage = state.firstNameErrorMessage,
                        imeAction = ImeAction.Done,
                        onClickDone = {
                            keyboardController?.hide()
                        }
                    )
                    TextFieldStandard(
                        label = "Last name",
                        value = state.lastName,
                        onValueChange = {
                            viewModel.onEvent(ChangeUserDataEvent.EnteredLastName(it))
                        },
                        errorMessage = state.lastNameErrorMessage,
                        imeAction = ImeAction.Done,
                        onClickDone = {
                            keyboardController?.hide()
                        }
                    )

                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                    ) {
                        Gender.values().forEach {
                            OptionPresent(
                                isChosen = state.gender == it,
                                value = it.name
                            ) {
                                viewModel.onEvent(ChangeUserDataEvent.ChooseGenderOption(it))
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(top = 8.dp, bottom = 16.dp)
                            .clickable {
                                dataPickerState.show()
                            }
                    ) {
                        Text(
                            text = "Date of birth: ",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${state.dateOfBirth ?: "date not chosen yet!"}"
                        )
                    }

                    TextFieldStandard(
                        label = "Phone number",
                        value = state.phoneNumber,
                        onValueChange = {
                            viewModel.onEvent(ChangeUserDataEvent.EnteredPhoneNumber(it))
                        },
                        errorMessage = state.phoneNumberErrorMessage,
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done,
                        onClickDone = {
                            keyboardController?.hide()
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Localization",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextFieldStandard(
                        label = "Country",
                        value = state.country,
                        onValueChange = {
                            viewModel.onEvent(ChangeUserDataEvent.EnteredCountry(it))
                        },
                        imeAction = ImeAction.Done,
                        onClickDone = {
                            keyboardController?.hide()
                        }
                    )

                    TextFieldStandard(
                        label = "City",
                        value = state.city,
                        onValueChange = {
                            viewModel.onEvent(ChangeUserDataEvent.EnteredCity(it))
                        },
                        imeAction = ImeAction.Done,
                        onClickDone = {
                            keyboardController?.hide()
                        }
                    )

                    TextFieldStandard(
                        label = "Street",
                        value = state.street,
                        onValueChange = {
                            viewModel.onEvent(ChangeUserDataEvent.EnteredStreet(it))
                        },
                        imeAction = ImeAction.Done,
                        onClickDone = {
                            keyboardController?.hide()
                        }
                    )

                    TextFieldStandard(
                        label = "State",
                        value = state.state,
                        onValueChange = {
                            viewModel.onEvent(ChangeUserDataEvent.EnteredState(it))
                        },
                        imeAction = ImeAction.Done,
                        onClickDone = {
                            keyboardController?.hide()
                        }
                    )
                }
            }
        }
    }

}
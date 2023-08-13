package com.example.blogapp.feature_blog.presentation.user_presentation.comp

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Face3
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.blogapp.core.domain.model.UserModel
import com.example.blogapp.feature_profile.presentation.change_user_data_presentation.Gender
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun UserDataPresentation(
    userModel: UserModel
) {
    val formattedTime = remember(userModel.dateOfBirth) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            userModel.dateOfBirth?.format(
                DateTimeFormatter.ofPattern("u LLLL d")
            )
        } else {
            val simpleDateFormat = SimpleDateFormat("yyyy MM dd", Locale.getDefault())
            simpleDateFormat.format(userModel.dateOfBirth)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (userModel.picture != null) {
                AsyncImage(
                    model = userModel.picture,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(200.dp)
                )
            } else {
                Icon(
                    imageVector = if (userModel.gender != null && userModel.gender == Gender.Female.toString()) Icons.Outlined.Face3 else Icons.Outlined.Face,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Info",
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        UserRowInfo(infoValue = "First name", value = userModel.firstName)
        UserRowInfo(infoValue = "Last name", value = userModel.lastName)
        UserRowInfo(infoValue = "Gender", value = userModel.gender ?: "none information")
        UserRowInfo(infoValue = "Birthday", value = formattedTime ?: "none information")
        UserRowInfo(infoValue = "Email", value = userModel.email)
        UserRowInfo(infoValue = "Phone number", value = userModel.phone ?: "none information")

        Spacer(modifier = Modifier.height(12.dp))

        UserRowInfo(infoValue = "Country", value = userModel.location.country ?: "none information")
        UserRowInfo(infoValue = "State", value = userModel.location.state ?: "none information")
        UserRowInfo(infoValue = "City", value = userModel.location.city ?: "none information")
        UserRowInfo(infoValue = "Street", value = userModel.location.street ?: "none information")
    }

}
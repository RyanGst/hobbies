package com.plcoding.jwtauthktorandroid.ui.books

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.jwtauthktorandroid.data.books.Book

@Composable
fun BookItem(book: Book) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = book.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = book.author,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )

            Row {
                Text(text = "$ ${book.price}")
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "--")
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = book.launchDate)
            }
        }
        Column {
            Row {
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.Green)
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Box {
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Delete", tint = Color.Red)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF, showSystemUi = true)
@Composable
fun BookItemPreview() {
    BookItem(Book(1, "Book 1", "Ryan", 1, "2401"))
}
@file:OptIn(ExperimentalLayoutApi::class)

package com.example.gjetpackcompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gjetpackcompose.ui.theme.GJetpackComposeTheme
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orders = loadOrdersFromAssets(this).sortedByDescending { it.date }
        setContent {
            GJetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserProfile(
                        modifier = Modifier.padding(innerPadding),
                        orders = orders,
                    )
                }
            }
        }
    }
}

@Composable
fun UserProfile(
    modifier: Modifier = Modifier,
    orders: List<Order> = emptyList(),
) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 5.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        // Profile Information Section
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
                contentDescription = "Left icon",
                tint = Color(0xFF049444),
                modifier = Modifier.size(30.dp)
            )
            // Profile Picture and Badge
            Box(
                modifier =
                    Modifier
                        .size(60.dp)
                        .background(Color(android.graphics.Color.parseColor("#00904A")), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "55",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "843***4455", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier =
                                Modifier
                                    .background(Color(android.graphics.Color.parseColor("#00904A")))
                                    .padding(horizontal = 5.dp),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.heart),
                                contentDescription = "Heart icon",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp),
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Theo dõi", fontSize = 14.sp, color = Color.White)
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "Check icon",
                        tint = Color(0xFF47af7c),
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Đã xác thực SĐT & Địa chỉ", fontSize = 14.sp)
                }
            }
        }

        FlowRow(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val categories =
                listOf(
                    "Đã mua",
                    "Thiết bị điện tử",
                    "Voucher",
                    "Thiết bị điện gia dụng",
                    "Mẹ và bé",
                    "Nhà cửa",
                )

            val colors =
                listOf(
                    null,
                    Color(0xFF4899B5),
                    Color(0xFFE6804D),
                    Color(0xFF2A5C73),
                    Color(0xFFED8582),
                    Color(0xFFD9AB0C),
                )

            categories.forEachIndexed { index, category ->
                val color = colors[index % colors.size]

                Text(
                    text = category,
                    modifier =
                        if (color != null) {
                            Modifier.background(color).padding(horizontal = 5.dp)
                        } else {
                            Modifier.padding(horizontal = 5.dp)
                        },
                    color = if (color != null) Color.White else Color.Gray,
                    fontWeight = if (color != null) FontWeight.Normal else FontWeight.Bold,
                    fontSize = 14.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.7.dp,
            color = Color.LightGray,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Stats Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "--", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "Độ cháy túi", fontSize = 12.sp, color = Color.Gray)
            }

            VerticalDivider(
                modifier = Modifier.width(0.7.dp).height(15.dp),
                color = Color.LightGray,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "80", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "ĐH đã đặt", fontSize = 12.sp, color = Color.Gray)
            }

            VerticalDivider(
                modifier = Modifier.width(0.7.dp).height(15.dp),
                color = Color.LightGray,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "--", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "Thành công", fontSize = 12.sp, color = Color.Gray)
            }

            VerticalDivider(
                modifier = Modifier.width(0.7.dp).height(15.dp),
                color = Color.LightGray,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_rocket_launch_24),
                        contentDescription = "Rocket icon",
                        tint = Color(0xFF47af7c),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Tên lửa", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = "Tốc độ nhận", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.7.dp,
            color = Color.LightGray,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ratings Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "38😍 10😠", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "Đánh giá", fontSize = 12.sp, color = Color.Gray)
            }

            VerticalDivider(
                modifier = Modifier.width(0.7.dp).height(15.dp),
                color = Color.LightGray,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "10 Shop", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "Đã ghé thăm", fontSize = 12.sp, color = Color.Gray)
            }

            VerticalDivider(
                modifier = Modifier.width(0.7.dp).height(15.dp),
                color = Color.LightGray,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "11 Shop", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "Đã mua", fontSize = 12.sp, color = Color.Gray)
            }

            VerticalDivider(
                modifier = Modifier.width(0.7.dp).height(15.dp),
                color = Color.LightGray,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(text = "--", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(text = "Chu kỳ mua", fontSize = 12.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 7.dp,
            color = Color(0xFFf1f0f6),
        )

        Column(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
        ) {
            Row {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Hoạt động",
                    color = Color(0xFF47af7c),
                    fontWeight = FontWeight.Bold,
                )
                Text(modifier = Modifier.padding(8.dp), text = "Nhận hàng")
                Text(modifier = Modifier.padding(8.dp), text = "2lanstore")
            }

            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                HorizontalDivider(
                    Modifier.width(95.dp),
                    thickness = 3.dp,
                    color = Color(0xFF47af7c),
                )
                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray,
                )
            }
        }

        Row {
            LazyColumn(
                modifier = Modifier.fillMaxSize().weight(4f),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                contentPadding = PaddingValues(8.dp),
            ) {
                items(orders) { order ->
                    OrderItemWithDivider(order, orders.indexOf(order) < orders.size - 1)
                }
            }

            Column(
                modifier = Modifier.weight(1f).padding(0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(70.dp).background(Color(0xFF049444)),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(4.dp),
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Icon(
                                painter = painterResource(id = R.drawable.call),
                                contentDescription = "Call icon",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp),
                            )
                            Text(
                                text = "(3)",
                                color = Color.White,
                                fontSize = 12.sp,
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Gọi điện",
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().height(70.dp).background(Color(0xFF049444)),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.chat),
                            contentDescription = "Chat icon",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Chat",
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().height(70.dp).background(Color(0xFF049444)),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Add icon",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tạo ĐH",
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth().height(70.dp).background(Color(0xFF049444)),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = "Star icon",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp),
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.circle_add),
                                contentDescription = "Circle Add icon",
                                tint = Color.White,
                                modifier =
                                    Modifier
                                        .size(18.dp)
                                        .background(Color(0xFF049444), CircleShape),
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Đánh giá",
                            color = Color.White,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    GJetpackComposeTheme {
        val context = LocalContext.current
        val orders = loadOrdersFromAssets(context)
        UserProfile(orders = orders)
    }
}

@Composable
fun OrderItem(order: Order) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "YEAHHH! ${order.userName} đã đặt hàng sản phẩm ${order.productName}",
            fontSize = 14.sp,
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = order.date,
            fontSize = 12.sp,
            color = Color.Gray,
            fontStyle = FontStyle.Italic,
        )
    }
}

@Composable
fun OrderItemWithDivider(
    order: Order,
    showDivider: Boolean,
) {
    Column {
        OrderItem(order)
        if (showDivider) {
            Spacer(modifier = Modifier.height(8.dp)) // Optional: add spacing if needed
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = Color.LightGray,
            )
        }
    }
}

fun loadOrdersFromAssets(context: Context): List<Order> {
    val jsonString =
        context.assets
            .open("orders.json")
            .bufferedReader()
            .use { it.readText() }
    val listType = object : TypeToken<List<Order>>() {}.type
    return Gson().fromJson(jsonString, listType)
}

data class Order(
    val userName: String,
    val productName: String,
    val date: String,
)

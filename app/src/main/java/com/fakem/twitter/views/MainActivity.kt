package com.fakem.twitter.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.fakem.twitter.R
import com.fakem.twitter.models.BottomBarItemInfo
import com.fakem.twitter.models.bottomBarBottomBarItems
import com.fakem.twitter.ui.theme.BlueTwitter
import com.fakem.twitter.ui.theme.TwitterTheme
import com.fakem.twitter.utils.AppBarItemType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomAppBarItems = remember { mutableStateOf(bottomBarBottomBarItems()) }
            val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed ))

            val scope = rememberCoroutineScope()
            TwitterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   Scaffold(
                       scaffoldState = scaffoldState,
                       topBar = { TopAppBarMain(){clickedItemAppBarItemType->
                           when(clickedItemAppBarItemType){
                               AppBarItemType.Profile ->{
                                   scope.launch {
                                       scaffoldState.drawerState.open()
                                   }
                               }
                               else -> {}
                           }
                       } },
                       drawerContent = { DrawerMenu(scaffoldState, scope) },
                       floatingActionButton = { FloatingActionButtonMain() },
                       floatingActionButtonPosition = FabPosition.End,
                       bottomBar = {
                           bottomAppBarItems.apply {
                               BottomBar(value){ selectedPosition ->
                                   bottomAppClickListener(value, selectedPosition){updatedList ->
                                       value = updatedList
                                   }
                               }
                           }
                       }

                   ){
                       Column(modifier = Modifier.padding(it)) {
                           HorizantalPager()
                       }
                   }
                }
            }
        }
    }
}

private fun bottomAppClickListener(currentList : List<BottomBarItemInfo>, selectedPosition: Int, updateBottomAppBar: (List<BottomBarItemInfo>) -> Unit) {
    val updatedList = mutableListOf<BottomBarItemInfo>()
    currentList.forEachIndexed { index, item ->
        updatedList.add(
            BottomBarItemInfo(image = item.image, label = item.label,
                isSelected = index == selectedPosition, contentDescription = null)
        )
    }
    updateBottomAppBar.invoke(updatedList)
}


@Composable
fun DrawerMenu(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ModalDrawer(
        drawerState = drawerState,
        drawerContent ={},
        content = {
            Column(modifier = Modifier.fillMaxSize()) {

                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (header, dividerContent,  content, space, dividerSettings, setting, options) = createRefs()
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(header) {
                            top.linkTo(parent.top)
                        }
                    ) {
                        PrimaryAccount(name = "Pixsellz", userName = "ayotunde", 216, 117)
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 20.dp)
                        .background(Color(0xffbdc5cd))
                        .constrainAs(dividerContent) {
                            top.linkTo(header.bottom)
                        }) {}


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(content) {
                                top.linkTo(dividerContent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }

                    ){
                        val drawableNavigations = mutableListOf<BottomBarItemInfo>()
                        drawableNavigations.addAll(
                            listOf(
                                BottomBarItemInfo(R.drawable.profile_stroke_icon, label = "Profile", contentDescription = null),
                                BottomBarItemInfo(R.drawable.lists_icon, label = "Lists", contentDescription = null),
                                BottomBarItemInfo(R.drawable.topics_stroke_icon, label = "Topics", contentDescription = null),
                                BottomBarItemInfo(R.drawable.bookmarks_icon, label = "Bookmarks", contentDescription = null),
                                BottomBarItemInfo(R.drawable.moments_icon, label = "Moments", contentDescription = null)
                            )
                        )

                        items(drawableNavigations){item->
                            DrawerNavigationItems(item)
                        }
                    }


                    Spacer(modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                        .constrainAs(space) {
                            top.linkTo(content.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = 20.dp)
                        .background(Color(0xffbdc5cd))
                        .constrainAs(dividerSettings) {
                            top.linkTo(space.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }) {}

                    LazyColumn(modifier = Modifier
                        .padding(top = 12.dp, start = 6.dp)
                        .constrainAs(setting) {
                            top.linkTo(dividerSettings.bottom)
                            start.linkTo(parent.start)
                        }){
                        val drawableNavigationItems = mutableListOf<BottomBarItemInfo>()
                        drawableNavigationItems.addAll(
                            listOf(
                                BottomBarItemInfo(image = null, label = "Settings and privacy", contentDescription = null),
                                BottomBarItemInfo(image = null, label = "Help Center", contentDescription = null),
                            )
                        )
                        items(drawableNavigationItems){item->
                            DrawerNavigationItems(item)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 22.dp, end = 20.dp, bottom = 32.dp)
                            .constrainAs(options) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Image(painter = painterResource(id = R.drawable.lamb), contentDescription = null )
                        Image(painter = painterResource(id = R.drawable.union) , contentDescription = null )

                    }

                }

            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizantalPager(){
    HorizontalPager(pageCount = 5 ) {
        Text(text = "mkkkk", modifier = Modifier.fillMaxSize().background(Color.Yellow))
    }
}

@Composable
fun DrawerNavigationItems(item: BottomBarItemInfo) {
    Row(modifier = Modifier.padding(top = 18.dp), verticalAlignment = Alignment.CenterVertically) {
        item.image?.let {
            Image(modifier = Modifier.padding(start = 25.dp), painter = painterResource(id = it), contentDescription = null)
        }
        Text(modifier = Modifier.padding(start = 25.dp), text = item.label, fontSize =18.sp, fontWeight = FontWeight.Normal, fontFamily = FontFamily.SansSerif)
    }
}

@Composable
fun TopAppBarMain(clickAppBarItemListener : (AppBarItemType) -> Unit) {
    TopAppBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            TopAppBarButtonMain(R.drawable.profile_photo, null, AppBarItemType.Profile){
                clickAppBarItemListener.invoke(it)
            }

            TopAppBarButtonMain(R.drawable.twitter_logo, null, AppBarItemType.Profile){

            }

            TopAppBarButtonMain(R.drawable.feature_stroke_icon, null, AppBarItemType.Profile){

            }
        }
    }
}

@Composable
fun PrimaryAccount(name : String, userName : String, following : Int, followers : Int){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 28.dp)) {
        val (primaryLayout, accounts) = createRefs()
        Column(
            modifier = Modifier.constrainAs(primaryLayout){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        ) {
            Image(modifier = Modifier.size(50.dp), painter = painterResource(id = R.drawable.profile_photo), contentDescription = null )
            Text(modifier = Modifier.padding(top = 12.dp), text = name, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Text(modifier = Modifier.padding(top = 0.dp), text = "@$userName", fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)) {
                Text(text = following.toString(), fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold )
                Text(text = "following", modifier = Modifier.padding(start = 4.dp))
                Text(text = followers.toString(), modifier = Modifier.padding(start = 14.dp), fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold )
                Text(text = "followers", modifier = Modifier.padding(start = 4.dp) )

            }
       
       
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.constrainAs(accounts){
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }) {
            Image(modifier = Modifier.padding(start = 16.dp), painter = painterResource(id = R.drawable.secondary_account), contentDescription = null )
            Image(modifier = Modifier.padding(start = 16.dp), painter = painterResource(id = R.drawable.secondary_account_2), contentDescription = null )
            Image(modifier = Modifier.padding(start = 16.dp), painter = painterResource(id = R.drawable.menu_icon), contentDescription = null )

        }
    }
}

@Composable
fun TopAppBarButtonMain(
    @DrawableRes profilePhoto: Int,
    contentDescription: String?,
    appBarItemType: AppBarItemType,
    appBarItemClickListener : (AppBarItemType) -> Unit
) {
    Button(onClick = {
        appBarItemClickListener.invoke(appBarItemType)
    },
        elevation = ButtonDefaults.elevation(0.dp,2.dp,0.dp,0.dp,0.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(6.dp)
    ) {
        Image(painter = painterResource(id = profilePhoto), contentDescription = contentDescription)
    }
}

@Composable
fun FloatingActionButtonMain(){
    val context = LocalContext.current
    FloatingActionButton(
        backgroundColor = BlueTwitter,
        onClick = {
            Toast.makeText(context, "float tıklandı", Toast.LENGTH_LONG).show()
                  },
        elevation =  FloatingActionButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        )
    ){
        Image(painter = painterResource(id = R.drawable.add_tweet_button), contentDescription = null)
    }
}

@Composable
fun BottomBar(bottomAppBarItems: List<BottomBarItemInfo>, onBottomItemClickLister: (Int) -> Unit) {
    BottomAppBar() {
        bottomAppBarItems.forEachIndexed() { position, item ->
            BottomNavigationItem(
                selected = item.isSelected,
                onClick = {
                    onBottomItemClickLister.invoke(position)
                },
                icon = {
                    item.image?.let {
                        Icon(
                            modifier = Modifier.blur(0.dp),
                            painter = painterResource(id = item.image),
                            contentDescription = item.contentDescription,
                            tint = item.tint(item.isSelected)
                        )
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TwitterTheme {
        FloatingActionButtonMain()
    }
}
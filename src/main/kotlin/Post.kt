import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.Date


data class Post(
    val fromID: Int,
    var text: String,
    var markedAsAds: Boolean = false,
    var isPinned: Boolean = false,
    var friendsOnly: Boolean = false,
    var postType: PostType = PostType.POST,
    var canDelete: Boolean = false,
    var isFavorite: Boolean = false,
) {
    private var id: Int? = null
    private var ownerID: Int? = null
    private var date: Int? = null
    private var comments: Comments = Comments(0, true, true, true, true)
    private var likes: Likes = Likes(0, false, true, true)

    fun fillOutOf(postChanged: Post): Post {
        val postAfter: Post = postChanged
        postAfter.date = this.date
        postAfter.id = this.id
        postAfter.ownerID = this.ownerID
        postAfter.comments = this.comments
        postAfter.likes = this.likes
        return postAfter
    }

    fun publish(ownerID: Int): Boolean = if (id == null) {
        //Эта функция вместо init блока, так как по логике задания пост должен "существовать" на "какой-то" стене
        Enumerator.countPosts += 1
        id = Enumerator.countPosts
        date = (currentTimeMillis() / 1000).toInt()
        this.ownerID = ownerID
        true
    } else false

    fun getOwnerID() = ownerID
    fun getID() = id
    fun getDate(): String =
        if (date != null) SimpleDateFormat("dd.MM.yyyy в HH:mm:ss").format(Date(date!!.toLong() * 1000))
        else "Запись ещё не опубликована"

    fun getDateUnixTime() = date
}

data class Comments(
    val count: Int,
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)
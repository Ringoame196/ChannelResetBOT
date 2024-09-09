package org.example.Events

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageType
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.example.EmbedData
import org.example.Managers.DiscordManager
import java.awt.Color

class CommandEvent : ListenerAdapter() {
    private val discordManager = DiscordManager()

    override fun onSlashCommandInteraction(e: SlashCommandInteractionEvent) {

        when (e.name) {
            "test" -> testCommand(e)
            "stop" -> stopCommand(e)
            "reset" -> resetCommand(e)
            else -> e.reply("不明なコマンドです。").queue()
        }
    }

    private fun testCommand(e: SlashCommandInteractionEvent) {
        val message = "テストだよ！"
        e.reply(message).queue()
    }

    private fun stopCommand(e: SlashCommandInteractionEvent) {
        val member = e.member // 送信者
        val jda = e.jda
        val message = "BOTの電源をオフにするよ！"

        if (member?.isOwner == true) { // 鯖の所有者のみ実行可能にする
            e.reply(message).queue()
            discordManager.shutdown(jda)
        }
    }

    private fun resetCommand(e: SlashCommandInteractionEvent) {
        val member = e.member // 送信者
        val messageCountToLoad = 80 // 取得するメッセージ数
        val textChannel = e.channel // テキストチャンネル
        if (member?.hasPermission(Permission.MESSAGE_MANAGE) == true) {
            // コルーチンを使用して非同期処理を実行
            CoroutineScope(Dispatchers.Default).launch {
                val messages = textChannel.history.retrievePast(messageCountToLoad).complete() // 指定数のメッセージを取得
                var deleteCount = 0 // メッセージを削除した回数
                var clearReactionCount = 0 // リアクションをクリアした回数

                val startMessage = "チャンネルリセット開始します"
                e.reply(startMessage).queue()

                for (message in messages) {
                    // ここでメッセージの内容を表示または処理
                    if (!message.isPinned && (message.type == MessageType.DEFAULT || message.type == MessageType.INLINE_REPLY || message.type == MessageType.SLASH_COMMAND)) {
                        message.delete().queue()
                        delay(1000L) // 1秒遅延
                        deleteCount++
                    } else if (message.reactions.size > 0) {
                        message.clearReactions().queue() // リアクションリセット
                        clearReactionCount++
                    }
                }
                sendEndMessage(textChannel, deleteCount, clearReactionCount) // 終了メッセージを送信
            }
        }
    }
    private fun sendEndMessage(textChannel: MessageChannelUnion, deleteCount:Int, clearReactionCount:Int) {
        val result: Map<String, String> = mapOf(
            "メッセージ削除数" to "${deleteCount}個",
            "リアクション削除" to "${clearReactionCount}個"
        )

        val endMessageEmbed = EmbedData(title = "[リセット]", description =  "「${textChannel.name}」チャンネルのリセット", color = Color.red,result)
        discordManager.sendEmbed(textChannel,endMessageEmbed)
    }
}
package com.alicia.repository

import com.alicia.data.Member
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import java.time.Instant
import java.util.UUID
import javax.inject.Singleton
import org.bson.Document

@Singleton
class MemberRepository(client: MongoClient) {
    private val db: MongoDatabase = client.getDatabase("member")
    private val collection: MongoCollection<Member> = db.getCollection("member", Member::class.java)

    fun findById(uuid: UUID): Member? =
        collection.find(Document("uuid", uuid.toString())).first()

    fun deleteById(uuid: UUID): DeleteResult = collection.deleteOne(Document("id", uuid.toString()))

    fun save(member: Member) : String? {
//        val document = Document("name", member.name)
//            .append("id", member.uuid.toString())
//            .append("accountStatus", member.accountStatus)
//            .append("lastLogin", member.lastLogin)
//            .append("createdDate", Instant.now())
//            .append("email", member.email)
//            .append("permissions", member.permissions)

        collection.insertOne(member)
        return member.uuid.toString()
    }
}
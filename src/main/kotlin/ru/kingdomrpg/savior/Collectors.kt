package ru.kingdomrpg.savior

import org.netbeans.lib.profiler.heap.Instance
import java.util.*

enum class Path(val skillCount: Int) {
    WAR(2),
    WORK(2),
    CRAFT(3),
    FOOD(3),
    MAGIC(2)
}

enum class CitizenStatus {
    KING,
    LORD,
    CITIZEN,
    NOT_LISTED
}

enum class KingdomInviteStatus {
    ACTIVE,
    INACTIVE
}

enum class SkillKey(val path: Path) {

    // work path
    LUMBERJACK(Path.WORK),
    MINER(Path.WORK),

    // war path
    WARRIOR(Path.WAR),
    ARCHER(Path.WAR),

    // craft path
    SMITH(Path.CRAFT),
    CARPENTER(Path.CRAFT),
    ENGINEER(Path.CRAFT),

    // food path
    FARMER(Path.FOOD),
    FISHER(Path.FOOD),
    COOK(Path.FOOD),

    // magic path
    ALCHEMIST(Path.MAGIC),
    ENCHANTER(Path.MAGIC),
}


data class SkillPathModel(
    val id: UUID,
    val path: Path,
    var xp: Double,
    var levels: Int
)

fun collectSkillPath(instance: Instance): SkillPathModel {
    val leastSigBits = instance.i("id").f<Long>("leastSigBits")
    val mostSigBits = instance.i("id").f<Long>("mostSigBits")

    val uuid = UUID(mostSigBits, leastSigBits)
    val pathOrdinal = instance.i("path").f<Int>("ordinal")
    val path = Path.values()[pathOrdinal]
    val xp = instance.f<Double>("xp")
    val levels = instance.f<Int>("levels")

    return SkillPathModel(
        uuid,
        path,
        xp,
        levels
    )
}


data class SkillLevelModel(
    val id: UUID,
    val skill: SkillKey,
    var level: Int
)

fun collectSkillLevel(instance: Instance): SkillLevelModel {
    val leastSigBits = instance.i("id").f<Long>("leastSigBits")
    val mostSigBits = instance.i("id").f<Long>("mostSigBits")

    val uuid = UUID(mostSigBits, leastSigBits)
    val skillOrdinal = instance.i("skill").f<Int>("ordinal")
    val skill = SkillKey.values()[skillOrdinal]

    val level = instance.f<Int>("level")


    return SkillLevelModel(
        uuid,
        skill,
        level
    )
}


data class TownChunkModel(
    val x: Int,
    val y: Int,
    var townId: UUID?,
    var isOuter: Boolean
)

fun collectTownChunks(instance: Instance): TownChunkModel {
    val townId = try {
        val s = instance.i("townId")
        val leastSigBits = s.f<Long>("leastSigBits")
        val mostSigBits = s.f<Long>("mostSigBits")
        UUID(mostSigBits, leastSigBits)
    } catch (e: Exception) {
        null
    }
    val x = instance.f<Int>("x")
    val y = instance.f<Int>("y")

    return TownChunkModel(
        x,
        y,
        townId,
        false
    )
}


data class TownModel(
    val id: UUID,
    val kingdomId: UUID,
    var name: String
)

fun collectTowns(instance: Instance): TownModel {

    val id = instance.uuid("id")
    val kingdomId = instance.uuid("kingdomId")
    val name = "!!!!!!!!!!!!!!!!!!!" // instance.ru.kingdomrpg.savior.string("name")

    return TownModel(
        id,
        kingdomId,
        name
    )
}


data class KingdomCitizenModel(
    val id: UUID,
    var kingdomId: UUID,
    var status: CitizenStatus
)

fun collectCitizens(instance: Instance): KingdomCitizenModel {

    val id = instance.uuid("id")
    val kingdomId = instance.uuid("kingdomId")
    val status = CitizenStatus.values()[instance.i("status").f<Int>("ordinal")]

    return KingdomCitizenModel(
        id,
        kingdomId,
        status
    )
}


data class KingdomModel(
    val id: UUID,
    var name: String,
    var king: UUID,
    var color: String
)


fun collectKingdoms(instance: Instance): KingdomModel {

    val id = instance.uuid("id")
    val name = "!!!!!!!!!!!!!!"
    val king = instance.uuid("king")
    val color = "color!!!!!!!"

    return KingdomModel(
        id,
        name,
        king,
        color
    )
}

data class KingdomRequestModel(
    val playerId: UUID,
    val kingdomId: UUID,
    var status: KingdomInviteStatus
)

fun collectRequests(instance: Instance): KingdomRequestModel {

    val playerId = instance.uuid("playerId")
    val kingdomId = instance.uuid("kingdomId")
    val status = KingdomInviteStatus.values()[instance.i("status").f<Int>("ordinal")]

    return KingdomRequestModel(
        playerId,
        kingdomId, status
    )
}

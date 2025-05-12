import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationPose

val mockMeditationPoses = listOf(
    MeditationPose(
        id = 1,
        name = "Lotussitz (Padmasana)",
        description = "Fördert Ruhe und stabile Haltung. Klassische Sitzhaltung für Meditation.",
        imageRes = R.drawable.pose_lotus
    ),
    MeditationPose(
        id = 2,
        name = "Halber Lotussitz",
        description = "Bequeme Sitzhaltung für Einsteiger mit weniger Flexibilität.",
        imageRes = R.drawable.pose_half_lotus
    ),
    MeditationPose(
        id = 3,
        name = "Burmese Pose",
        description = "Sanfte Kreuzbein-Sitzhaltung, ideal für längere Meditationen.",
        imageRes = R.drawable.pose_burmese
    ),
    MeditationPose(
        id = 4,
        name = "Fersensitz (Vajrasana)",
        description = "Stabile Haltung auf den Fersen, gut bei Rückenschmerzen.",
        imageRes = R.drawable.pose_vajrasana
    ),
    MeditationPose(
        id = 5,
        name = "Stuhlhaltung",
        description = "Für alle mit Bewegungseinschränkungen – aufrecht auf einem Stuhl meditieren.",
        imageRes = R.drawable.pose_chair
    ),
    MeditationPose(
        id = 6,
        name = "Liegende Meditation (Shavasana)",
        description = "Entspannter Rückenlage, besonders bei Body-Scan geeignet.",
        imageRes = R.drawable.pose_savasana
    )
)
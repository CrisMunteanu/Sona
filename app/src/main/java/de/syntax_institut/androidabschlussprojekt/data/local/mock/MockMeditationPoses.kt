import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.domain.model.MeditationPose

val mockMeditationPoses = listOf(
    MeditationPose(
        id = 1,
        name = "Lotussitz (Padmasana)",
        description = "Fördert Ruhe und stabile Haltung. Klassische Sitzhaltung für Meditation.",
        imageRes = R.drawable.pose_lotus,
        longDescription = "Der Lotussitz ist eine der bekanntesten Meditationshaltungen. Dabei werden die Beine überkreuzt, sodass jeder Fuß auf dem gegenüberliegenden Oberschenkel liegt. Diese Haltung schafft eine stabile Basis, öffnet die Hüften und richtet die Wirbelsäule natürlich auf. Besonders in der buddhistischen und yogischen Praxis ist sie ein Symbol für geistige Erhebung."
    ),
    MeditationPose(
        id = 2,
        name = "Halber Lotussitz",
        description = "Bequeme Sitzhaltung für Einsteiger mit weniger Flexibilität.",
        imageRes = R.drawable.pose_half_lotus,
        longDescription = "Der halbe Lotussitz ist eine gute Alternative zum vollen Lotussitz. Ein Fuß liegt auf dem gegenüberliegenden Oberschenkel, während das andere Bein einfach unterliegt. Diese Haltung ist für viele Menschen bequemer, besonders bei eingeschränkter Hüftbeweglichkeit. Sie sorgt für Stabilität und eine aufrechte Körperhaltung."
    ),
    MeditationPose(
        id = 3,
        name = "Burmese Pose",
        description = "Sanfte Kreuzbein-Sitzhaltung, ideal für längere Meditationen.",
        imageRes = R.drawable.pose_burmese,
        longDescription = "Die Burmese Pose ist eine entspannte Variante des Sitzens mit gekreuzten Beinen, bei der die Beine nebeneinander liegen und sich nicht überkreuzen. Sie ist besonders für Anfänger geeignet und entlastet die Knie und Hüften. Die Position fördert eine ruhige Haltung ohne unnötige Muskelanspannung."
    ),
    MeditationPose(
        id = 4,
        name = "Fersensitz (Vajrasana)",
        description = "Stabile Haltung auf den Fersen, gut bei Rückenschmerzen.",
        imageRes = R.drawable.pose_vajrasana,
        longDescription = "Beim Fersensitz kniet man sich hin und setzt sich auf die Fersen. Diese Haltung wird oft nach dem Essen oder zur Verdauungsförderung empfohlen. Sie richtet die Wirbelsäule auf und ist besonders stabil. Für Menschen mit Knieproblemen kann ein Kissen zwischen Gesäß und Fersen helfen."
    ),
    MeditationPose(
        id = 5,
        name = "Stuhlhaltung",
        description = "Für alle mit Bewegungseinschränkungen – aufrecht auf einem Stuhl meditieren.",
        imageRes = R.drawable.pose_chair,
        longDescription = "Die Meditation im Sitzen auf einem Stuhl ist eine hervorragende Option für Menschen mit körperlichen Einschränkungen oder ältere Personen. Die Füße stehen flach auf dem Boden, der Rücken ist gerade, und die Hände liegen entspannt auf den Oberschenkeln. Diese Haltung erlaubt Achtsamkeit ohne körperliche Belastung."
    ),
    MeditationPose(
        id = 6,
        name = "Liegende Meditation (Shavasana)",
        description = "Entspannter Rückenlage, besonders bei Body-Scan geeignet.",
        imageRes = R.drawable.pose_savasana,
        longDescription = "Die liegende Haltung Shavasana ist ideal für Tiefenentspannung, Body-Scan oder Einschlafmeditationen. Der Körper liegt flach auf dem Rücken, die Arme etwas vom Körper entfernt, Handflächen nach oben. Diese Haltung erlaubt völlige Entspannung und ist besonders hilfreich für den bewussten Umgang mit dem Atem."
    )
)
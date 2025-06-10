package de.syntax_institut.androidabschlussprojekt.data.repository



import de.syntax_institut.androidabschlussprojekt.domain.model.BuddhistText

object BuddhistTextRepository {

    fun getTexts(languageCode: String): List<BuddhistText> {
        return when (languageCode) {
            "de" -> listOf(
                BuddhistText("metta", "Metta Sutta", "Mögen alle Wesen glücklich sein...\nMögen sie in Sicherheit und Frieden leben...\nMögen alle Lebewesen frei von Leid, Hass und Furcht sein.", "de"),
                BuddhistText("dhamma", "Dhammapada Vers 1", "Alles, was wir sind, ist das Resultat dessen, was wir gedacht haben...\nDer Geist geht allem voran...\nMit reinem Geist erschaffen wir die Welt.", "de"),
                BuddhistText("buddha", "Buddham Saranam Gacchami", "Ich nehme Zuflucht zum Buddha...\nZur Lehre...\nZur Gemeinschaft.", "de"),
                BuddhistText("heart", "Herz-Sutra", "Form ist Leere, Leere ist Form...\nKein Entstehen, kein Vergehen...", "de"),
                BuddhistText("refuge", "Dreifache Zuflucht", "Ich nehme Zuflucht zum Buddha...\nZum Dhamma...\nZur Sangha.", "de"),
                BuddhistText("imperm", "Vergänglichkeit", "Alle Dinge sind vergänglich...\nEntstanden aus Ursachen, vergehen sie wieder.", "de")
            )

            "en" -> listOf(
                BuddhistText("metta", "Metta Sutta", "May all beings be happy...\nMay they live in safety and peace...\nFree from suffering, hatred, and fear.", "en"),
                BuddhistText("dhamma", "Dhammapada Verse 1", "All that we are is the result of what we have thought...\nThe mind precedes everything...\nWith a pure mind, we create the world.", "en"),
                BuddhistText("buddha", "Buddham Saranam Gacchami", "I go to the Buddha for refuge...\nThe Dhamma...\nThe Sangha.", "en"),
                BuddhistText("heart", "Heart Sutra", "Form is emptiness, emptiness is form...\nNo arising, no ceasing...", "en"),
                BuddhistText("refuge", "Threefold Refuge", "I take refuge in the Buddha...\nIn the Dhamma...\nIn the Sangha.", "en"),
                BuddhistText("imperm", "Impermanence", "All things are impermanent...\nArising from causes, they pass away.", "en")
            )

            "fr" -> listOf(
                BuddhistText("metta", "Metta Sutta", "Que tous les êtres soient heureux...\nQu'ils vivent en sécurité et en paix...", "fr"),
                BuddhistText("dhamma", "Dhammapada Vers 1", "Tout ce que nous sommes est le résultat de ce que nous avons pensé...\nL'esprit précède tout...", "fr"),
                BuddhistText("buddha", "Buddham Saranam Gacchami", "Je vais vers le Bouddha pour refuge...\nVers le Dharma...\nVers la Sangha.", "fr"),
                BuddhistText("heart", "Sutra du Cœur", "La forme est vide, le vide est forme...\nNi naissance ni cessation...", "fr"),
                BuddhistText("refuge", "Triple Refuge", "Je prends refuge dans le Bouddha...\nLe Dharma...\nLa Sangha.", "fr"),
                BuddhistText("imperm", "Impermanence", "Toutes choses sont impermanentes...\nIssues de causes, elles disparaissent.", "fr")
            )

            "es" -> listOf(
                BuddhistText("metta", "Metta Sutta", "Que todos los seres sean felices...\nQue vivan en seguridad y paz...", "es"),
                BuddhistText("dhamma", "Dhammapada Verso 1", "Todo lo que somos es el resultado de lo que hemos pensado...\nLa mente lo precede todo...", "es"),
                BuddhistText("buddha", "Buddham Saranam Gacchami", "Me refugio en el Buda...\nEn el Dharma...\nEn la Sangha.", "es"),
                BuddhistText("heart", "Sutra del Corazón", "La forma es vacío, el vacío es forma...\nNo hay nacimiento ni cesación...", "es"),
                BuddhistText("refuge", "Triple Refugio", "Tomo refugio en el Buda...\nEn el Dharma...\nEn la Sangha.", "es"),
                BuddhistText("imperm", "Impermanencia", "Todas las cosas son impermanentes...\nSurgidas de causas, desaparecen.", "es")
            )

            else -> emptyList()
        }
    }
}
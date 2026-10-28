package app.gamenative.service.epic

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for EpicGameLauncher.resolveEpicUserValue()
 *
 * Covers the offline save-folder bug: -epicuserid must resolve to the real persisted
 * account id so the game reads/writes the same per-user save folder it uses online,
 * and only fall back to the placeholder when there are genuinely no stored credentials.
 */
class EpicGameLauncherTest {

    @Test
    fun `online token value wins`() {
        assertEquals(
            "token-account",
            EpicGameLauncher.resolveEpicUserValue("token-account", "stored-account", "0"),
        )
    }

    @Test
    fun `offline falls back to stored value when token is null`() {
        assertEquals(
            "stored-account",
            EpicGameLauncher.resolveEpicUserValue(null, "stored-account", "0"),
        )
    }

    @Test
    fun `blank token value falls through to stored value`() {
        assertEquals(
            "stored-account",
            EpicGameLauncher.resolveEpicUserValue("", "stored-account", "0"),
        )
    }

    @Test
    fun `blank stored value does not slip through to an empty id`() {
        assertEquals(
            "0",
            EpicGameLauncher.resolveEpicUserValue(null, "", "0"),
        )
    }

    @Test
    fun `fallback used only when no credentials at all`() {
        assertEquals(
            "0",
            EpicGameLauncher.resolveEpicUserValue(null, null, "0"),
        )
    }

    @Test
    fun `display name falls back to EpicUser when nothing stored`() {
        assertEquals(
            "EpicUser",
            EpicGameLauncher.resolveEpicUserValue(null, null, "EpicUser"),
        )
    }
}

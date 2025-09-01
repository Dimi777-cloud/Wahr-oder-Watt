<script setup>
import { ref, onMounted } from 'vue';
import { UserCircleIcon } from '@heroicons/vue/24/solid';

const emit = defineEmits(['show-lobby']);


const loggedInUser = ref({ id: null, name: '' });
const highscores = ref([]);
const loading = ref(false);
const loadError = ref(null);

async function fetchHighscores(controller = new AbortController()) {
  loading.value = true;
  loadError.value = null;
  const timeout = setTimeout(() => controller.abort(), 8000);
  try {
    const resp = await fetch('/api/highscores', {
      method: 'GET',
      headers: { 'Accept': 'application/json' },
      credentials: 'include',
      signal: controller.signal
    });
    if (!resp.ok) throw new Error('HTTP ' + resp.status);
    const data = await resp.json();
    highscores.value = (Array.isArray(data) ? data : [])
        .map(h => ({
          name: h.id ?? h.userName ?? h.playerName ?? 'Unbekannt',
          score: h.score ?? 0
        }));
  } catch (e) {
    loadError.value = e.message;
    highscores.value = [];
  } finally {
    clearTimeout(timeout);
    loading.value = false;
  }
}
onMounted(async () => {
  await Promise.all([fetchHighscores()]);
});

function goBackToLobby() {
  emit('show-lobby');
}
</script>

<template>
  <div class="bg-gray-50 min-h-screen p-4 sm:p-8">
    <div class="max-w-4xl mx-auto bg-white p-6 rounded-2xl shadow-sm">
      
      <header class="flex justify-between items-center mb-10">
        <div class="flex items-center gap-3">
          <UserCircleIcon class="h-10 w-10 text-gray-500" />
          <span class="text-xl font-semibold text-gray-800">{{ loggedInUser.name }}</span>
        </div>
        <div class="w-24 h-16 bg-gray-100 rounded-lg flex items-center justify-center">
          <img src="../assets/logo.svg" alt="Logo" class="h-10">
        </div>
      </header>

      <main class="flex flex-col items-center">
        <h1 class="text-4xl font-bold text-gray-900 mb-8">Highscores</h1>

        <div class="w-full max-w-md border border-gray-200 rounded-lg overflow-hidden">
          <div class="grid grid-cols-2 bg-gray-100 font-semibold text-gray-700 p-3">
            <span>User</span>
            <span>Score</span>
          </div>
          <ul>
            <li 
              v-for="entry in highscores" 
              :key="entry.name"
              class="grid grid-cols-2 p-3 border-t border-gray-200"
            >
              <span>{{ entry.name }}</span>
              <span>{{ entry.score }}</span>
            </li>
          </ul>
        </div>

        <button @click="goBackToLobby" class="mt-12 bg-gray-200 text-gray-800 font-bold py-3 px-12 rounded-lg hover:bg-gray-300 transition-colors">
          Zurück
        </button>
      </main>

    </div>
  </div>
</template>
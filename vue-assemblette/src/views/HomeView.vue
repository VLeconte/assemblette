<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import DeputyCard from '@/components/DeputyCard.vue';
import { type Deputy } from '@/entities/deputy';
import DeputiesService from '@/services/deputies-service';
import { useMainStore } from '@/store/store';

const store = useMainStore()
const deputiesService = new DeputiesService();

const deputies = reactive<{
  data: Deputy[],
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

onMounted(
  async () => {
    try {
      deputies.data = await deputiesService.getDeputies()
      deputies.isLoading = false
    } catch (error) {
      console.error('Error fetching deputies', error)
    }
  }
);

onMounted(
  store.getMandatesByDeputies
);

</script>

<template>
  <div v-if="!deputies.isLoading && !store.mandatesByDeputies.isLoading"
    class="w-screen flex flex-wrap gap-8 p-4 justify-center">
    <DeputyCard v-for="deputy in deputies.data" :key="deputy.id" :deputy="deputy"
      :mandates="store.mandatesByDeputies.data[deputy.id]" />
  </div>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import DeputyCard from '@/components/DeputyCard.vue';
import { type Deputy } from '@/entities/deputy';
import DeputiesService from '@/services/deputies-service';

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
    } catch (error) {
      console.error('Error fetching deputies', error)
    }
  }
);

</script>

<template>
  <div class="grid grid-cols-6">
    <DeputyCard v-for="deputy in deputies.data" :key="deputy.id" :deputy="deputy" />
  </div>
</template>

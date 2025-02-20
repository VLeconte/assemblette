<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import DeputyCard from '@/components/DeputyCard.vue';
import { type Deputy } from '@/entities/deputy';
import DeputiesService from '@/services/deputies-service';
import MandatesService from '@/services/mandates-service';
import type { Mandate } from '@/entities/mandate';
import _ from 'lodash';

const deputiesService = new DeputiesService();
const mandatesService = new MandatesService();

const deputies = reactive<{
  data: Deputy[],
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});
const mandatesByDeputies = ref<Record<string, Mandate[]>>({})

onMounted(
  async () => {
    try {
      console.time("Loading deputies")
      deputies.data = await deputiesService.getDeputies()
      const mandates: Mandate[] = await mandatesService.getMandates()
      mandatesByDeputies.value = _.groupBy(mandates, (mandate) => mandate.deputy.id)
      deputies.isLoading = false
      console.timeLog("Loading deputies")
    } catch (error) {
      console.error('Error fetching deputies', error)
    }
  }
);

</script>

<template>
  <div v-if="!deputies.isLoading" class="w-screen flex flex-wrap gap-8 p-4 justify-center">
    <DeputyCard v-for="deputy in deputies.data" :key="deputy.id" :deputy="deputy"
      :mandates="mandatesByDeputies[deputy.id]" />
  </div>
</template>

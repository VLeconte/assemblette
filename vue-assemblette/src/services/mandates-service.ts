import axios from 'axios'

const restMandates = axios.create({
  // better still, use env vars to define your URLs
  baseURL: 'http://localhost:8080/api/mandates',
})

export default class MandatesService {
  public getMandates = async () => {
    try {
      return (await restMandates.get('')).data
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        console.error(err.toJSON())
        throw new Error(err.message)
      } else {
        console.error(err)
        throw new Error('An unexpected error occurred')
      }
    }
  }
}
